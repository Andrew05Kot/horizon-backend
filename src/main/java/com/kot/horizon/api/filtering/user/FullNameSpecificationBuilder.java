package com.kot.horizon.api.filtering.user;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.filtering.specifications.SpecificationBuilder;
import com.kot.horizon.api.searching.SearchCriteria;
import com.kot.horizon.model.user.UserEntity;

public class FullNameSpecificationBuilder implements SpecificationBuilder<UserEntity> {

    public static final List<FilteringOperation> SUPPORTED_OPERATORS = Arrays.asList(
            FilteringOperation.CONTAIN);

    @Override
    public Specification<UserEntity> buildSpecification(SearchCriteria searchCriteria) {
        return new FullNameSpecification(searchCriteria);
    }
}