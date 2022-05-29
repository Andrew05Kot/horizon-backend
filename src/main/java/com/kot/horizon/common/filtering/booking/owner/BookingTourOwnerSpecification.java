package com.kot.horizon.common.filtering.booking.owner;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.booking.BookingEntity;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;

public class BookingTourOwnerSpecification implements Specification<BookingEntity> {

	private static final long serialVersionUID = 6780218411885895782L;
	private SearchCriteria searchCriteria;

	public BookingTourOwnerSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<BookingEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		if (searchCriteria.getValue() != null) {
			List<Predicate> predicates = new ArrayList<>();
			if (FilteringOperation.EQUAL == searchCriteria.getOperation()) {
				predicates.add(criteriaBuilder.equal(root.get("tour").get("owner").get("id"), searchCriteria.getValue()));
			}
			return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		BookingTourOwnerSpecification that = (BookingTourOwnerSpecification) o;

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
