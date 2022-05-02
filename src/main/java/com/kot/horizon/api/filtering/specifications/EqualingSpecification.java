package com.kot.horizon.api.filtering.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.searching.SearchCriteria;

public class EqualingSpecification< EntityType > implements Specification< EntityType > {

	private static final long serialVersionUID = -5803891540465642051L;
	private SearchCriteria searchCriteria;

	public EqualingSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root< EntityType > root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (FilteringOperation.EQUAL == searchCriteria.getOperation()) {
			return cb.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
		} else if (FilteringOperation.NOT_EQUAL == searchCriteria.getOperation()) {
			return cb.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue());
		} else {
			return null;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof EqualingSpecification)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		final EqualingSpecification otherObject = (EqualingSpecification) o;

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