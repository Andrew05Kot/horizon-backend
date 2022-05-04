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

public class UserPermissionsSpecification implements Specification<UserEntity> {

	private static final long serialVersionUID = 3915784571698885571L;
	private SearchCriteria searchCriteria;

	public UserPermissionsSpecification(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		if (searchCriteria.getValue() != null) {
			List<Predicate> predicates = new ArrayList<>();
			if (FilteringOperation.CONTAIN == searchCriteria.getOperation()) {
				predicates.add(cb.isMember(searchCriteria.getValue(), root.get("userPermissions")));
			}
			return cb.or(predicates.toArray(new Predicate[predicates.size()]));
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof UserPermissionsSpecification)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		final UserPermissionsSpecification otherObject = (UserPermissionsSpecification) o;

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
