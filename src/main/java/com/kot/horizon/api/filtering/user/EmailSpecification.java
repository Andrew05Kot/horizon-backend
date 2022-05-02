package com.kot.horizon.api.filtering.user;

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
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.searching.SearchCriteria;
import com.kot.horizon.model.user.UserEntity;

public class EmailSpecification implements Specification<UserEntity> {

    private static final long serialVersionUID = 7860218411005895782L;
    private final SearchCriteria searchCriteria;

    public EmailSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery< ? > criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getValue() != null) {
            List<Predicate> predicates = new ArrayList<>();
            if (FilteringOperation.CONTAIN == searchCriteria.getOperation()) {
                String searchValueLowerCase = StringUtils.lowerCase((String) searchCriteria.getValue());
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + searchValueLowerCase + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmailSpecification)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        final EmailSpecification otherObject = (EmailSpecification) o;

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
