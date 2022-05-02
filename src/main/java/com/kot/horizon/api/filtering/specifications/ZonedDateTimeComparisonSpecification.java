package com.kot.horizon.api.filtering.specifications;

import java.time.ZonedDateTime;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.filtering.searchcriteria.converters.ObjectToZonedDateTimeConverter;
import com.kot.horizon.api.searching.SearchCriteria;

public class ZonedDateTimeComparisonSpecification<EntityType> implements Specification<EntityType> {

	private static final long serialVersionUID = 4426125158308605830L;

	private SearchCriteria searchCriteria;
	
	private ObjectToZonedDateTimeConverter converter = new ObjectToZonedDateTimeConverter();

	public ZonedDateTimeComparisonSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<EntityType> root, CriteriaQuery<?>query, CriteriaBuilder cb) {
		ZonedDateTime value = converter.convert(searchCriteria.getValue());
		if (FilteringOperation.GREATER_THEN == searchCriteria.getOperation()) {
			return cb.greaterThan(root.get(searchCriteria.getKey()), value);
			
		} else if (FilteringOperation.LESS_THEN == searchCriteria.getOperation()) {
			return cb.lessThan(root.get(searchCriteria.getKey()), value);
		} else if (FilteringOperation.GREATER_OR_EQUAL == searchCriteria.getOperation()) {
			return cb.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), value);
		} else if (FilteringOperation.LESS_OR_EQUAL == searchCriteria.getOperation()) {
			return cb.lessThanOrEqualTo(root.get(searchCriteria.getKey()), value);
		} else if (FilteringOperation.EQUAL == searchCriteria.getOperation()) {
			return cb.equal(root.get(searchCriteria.getKey()), value);
		} else if (FilteringOperation.NOT_EQUAL == searchCriteria.getOperation()) {
			return cb.notEqual(root.get(searchCriteria.getKey()), value);
		} else {
			return null;
		}
	}

}
