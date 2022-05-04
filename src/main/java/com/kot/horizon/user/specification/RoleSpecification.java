package com.kot.horizon.user.specification;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.user.model.UserEntity;

public class RoleSpecification implements Specification<UserEntity> {

	private final SearchCriteria searchCriteria;

	public RoleSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery< ? > criteriaQuery, CriteriaBuilder criteriaBuilder) {
		if (searchCriteria.getValue() != null) {
			List< Predicate > predicates = new ArrayList<>();
			if (FilteringOperation.EQUAL == searchCriteria.getOperation()) {
				predicates.add(criteriaBuilder.equal(root.get("role"), searchCriteria.getValue()));
			}
			return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof RoleSpecification)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		final RoleSpecification otherObject = (RoleSpecification) o;

		return new EqualsBuilder()
				.append(this.searchCriteria, otherObject.searchCriteria)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(searchCriteria)
				.hashCode();
	}
}
