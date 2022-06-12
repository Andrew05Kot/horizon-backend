package com.kot.horizon.common.filtering.tour.nameordescription;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.tour.TourEntity;

public class TourNameOrDescriptionSpecification implements Specification<TourEntity> {

	private static final long serialVersionUID = 7863318411885895782L;

	private SearchCriteria searchCriteria;

	public TourNameOrDescriptionSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}


	@Override
	public Predicate toPredicate(Root<TourEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if (searchCriteria.getValue() != null) {
			List<Predicate> predicates = new ArrayList<>();
			if (FilteringOperation.CONTAIN == searchCriteria.getOperation()) {
				String searchValueLowerCase = StringUtils.lowerCase((String) searchCriteria.getValue());
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchValueLowerCase + "%"));
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + searchValueLowerCase + "%"));
			}
			return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		TourNameOrDescriptionSpecification that = (TourNameOrDescriptionSpecification) o;

		return new EqualsBuilder()
				.append(searchCriteria, that.searchCriteria)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(searchCriteria)
				.toHashCode();
	}
}
