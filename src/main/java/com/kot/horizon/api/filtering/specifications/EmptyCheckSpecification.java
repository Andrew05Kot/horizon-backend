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

public class EmptyCheckSpecification<Entity> implements Specification<Entity> {

	private static final long serialVersionUID = -1440074011599064217L;
	private SearchCriteria searchCriteria;

	public EmptyCheckSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();
		if (FilteringOperation.EMPTY == searchCriteria.getOperation()) {
			predicates.add(cb.isEmpty(root.get(searchCriteria.getKey())));
		}
		return cb.or(predicates.toArray(new Predicate[predicates.size()]));
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof EmptyCheckSpecification)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		final EmptyCheckSpecification otherObject = (EmptyCheckSpecification) o;

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