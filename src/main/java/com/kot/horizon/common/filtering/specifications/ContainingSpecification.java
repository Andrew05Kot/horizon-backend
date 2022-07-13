package com.kot.horizon.common.filtering.specifications;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;

public class ContainingSpecification<Entity> implements Specification<Entity> {

	private static final long serialVersionUID = 7860218411005895782L;
	private SearchCriteria searchCriteria;

	public ContainingSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (searchCriteria.getValue() != null) {
			List<Predicate> predicates = new ArrayList<>();
			if (FilteringOperation.CONTAIN == searchCriteria.getOperation()) {
				String searchValueLowerCase = StringUtils.lowerCase((String) searchCriteria.getValue());
				predicates.add(cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + searchValueLowerCase + "%"));
			}
			return cb.or(predicates.toArray(new Predicate[predicates.size()]));
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ContainingSpecification)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		final ContainingSpecification otherObject = (ContainingSpecification) o;

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