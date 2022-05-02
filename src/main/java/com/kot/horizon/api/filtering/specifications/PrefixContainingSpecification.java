package com.kot.horizon.api.filtering.specifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.searching.SearchCriteria;

public class PrefixContainingSpecification<Entity> implements Specification<Entity> {

	private static final long serialVersionUID = 7860218411005895782L;
	private SearchCriteria searchCriteria;

	public PrefixContainingSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (searchCriteria.getValue() != null) {
			List<Predicate> predicates = new ArrayList<>();
			if (FilteringOperation.CONTAIN == searchCriteria.getOperation()) {
				String searchValue = (String) searchCriteria.getValue();
				predicates.add(cb.like(cb.substring(root.get(searchCriteria.getKey()),1, searchValue.length()), "%" + searchValue + "%"));
			}
			return cb.or(predicates.toArray(new Predicate[predicates.size()]));
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PrefixContainingSpecification)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		final PrefixContainingSpecification otherObject = (PrefixContainingSpecification) o;

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
