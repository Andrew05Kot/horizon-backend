package com.kot.horizon.api.v1.general;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.common.filtering.FilterableProperty;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.SearchCriteriaParser;
import com.kot.horizon.api.v1.ResponsePage;
import com.kot.horizon.architecture.model.BaseEntity;
import com.kot.horizon.architecture.service.AbstractService;

public abstract class AbstractAPIService<
			Entity extends BaseEntity,
			RequestBean extends AbstractRequest,
			ResponseBean extends AbstractResponse,
			Service extends AbstractService<Entity>> {

	public static final Integer DEFAULT_PAGE_NUMBER = 0;
	public static final Integer DEFAULT_PAGE_SIZE = 8;
	public static final Integer MAX_PAGE_SIZE = 64;
	public static final String NON_FOUND_MESSAGE = "Item was not found";

	@Autowired
	private SearchCriteriaParser searchCriteriaParser;

	@Autowired
	protected Service service;

	public ResponseBean create(RequestBean request) {
		Entity entity = getNewEntity(request);
		Entity createdEntity = service.create(entity);
		return convertToResponseBean(createdEntity, Optional.empty());
	}

	public void update(Long id, RequestBean request) {
		Entity entity = getValidEntityById(id);
		copyProperties(request, entity);
		service.update(entity);
	}

	public ResponseBean findById(Long id, Optional<String> expand) {
		Entity entity = getValidEntityById(id);
		return convertToResponseBean(entity, expand);
	}

	public ResponsePage<ResponseBean> findItems(Optional<String> search, Optional<Integer> pageNo,
												Optional<Integer> pageSize, Sort sort, Optional<String> expand) {
		Specification<Entity> filteringSpecification = null;
		if (search.isPresent()) {
			List<FilterableProperty<Entity>> filterableProperties = getSpecificationBuilder().getFilterableProperties();
			List<SearchCriteria> searchCriteria = searchCriteriaParser.parseSearchCriteria(search.get(),
					filterableProperties);
			filteringSpecification = getSpecificationBuilder().buildSpecification(searchCriteria);
		}
		return getResponsePage(filteringSpecification, pageNo.orElse(DEFAULT_PAGE_NUMBER),
				pageSize.orElse(DEFAULT_PAGE_SIZE), sort, expand);
	}

	public ResponsePage<ResponseBean> getResponsePage(Specification<Entity> filteringSpecification, Integer pageNo,
			Integer pageSize, Sort sort, Optional<String> expand) {
		pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
		sort = getSortedByDefault(sort);
		Pageable paging = PageRequest.of(pageNo, pageSize, sort);
		Page<Entity> pagedResult = service.findAll(filteringSpecification, paging);
		List<ResponseBean> responses = pagedResult.getContent().stream().map(item -> convertToResponseBean(item, expand))
				.collect(Collectors.toList());
		return new ResponsePage<>(responses, pagedResult.getTotalElements());
	}

	public void delete(Long id) {
		service.delete(id);
	}

	protected Sort getSortedByDefault(Sort sort) {
		return sort.and(Sort.by(Sort.Direction.ASC, "id"));
	}

	protected List<String> parseExpandField(Optional<String> expandFields) {
		return expandFields.map(fields -> Arrays.asList(fields.split(","))).orElse(Collections.emptyList());
	}

	protected abstract void patch( Long id, RequestBean request );

	protected abstract EntityFilterSpecificationsBuilder<Entity> getSpecificationBuilder();

	protected Entity getValidEntityById(Long id) {
		Entity entity = service.findById(id);
		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NON_FOUND_MESSAGE);
		}

		return entity;
	}

	protected abstract Entity getNewEntity(RequestBean request);

	protected abstract void copyProperties(RequestBean request, Entity entity);

	protected abstract ResponseBean convertToResponseBean(Entity entity, Optional<String> expand);
}
