package com.kot.horizon.user.specification;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.specifications.SpecificationBuilder;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.user.model.UserEntity;

public class EmailSpecificationBuilder implements SpecificationBuilder<UserEntity> {

    public static final List<FilteringOperation> SUPPORTED_OPERATORS = Arrays.asList(
            FilteringOperation.CONTAIN);

    @Override
    public Specification<UserEntity> buildSpecification(SearchCriteria searchCriteria) {
        return new EmailSpecification(searchCriteria);
    }
}