package com.kot.horizon.user.specification;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.common.filtering.FilterableProperty;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserRole;

@Component
public class UserSpecificationsBuilder implements EntityFilterSpecificationsBuilder<UserEntity> {

    public static final List<FilterableProperty<UserEntity>> FILTERABLE_PROPERTIES = Arrays.asList(
            new FilterableProperty<>("fullName", String.class, FullNameSpecificationBuilder.SUPPORTED_OPERATORS, new FullNameSpecificationBuilder()),
            new FilterableProperty<>("email", String.class, EmailSpecificationBuilder.SUPPORTED_OPERATORS, new EmailSpecificationBuilder()),
            new FilterableProperty<>("role", UserRole.class, RoleSpecificationBuilder.SUPPORTED_OPERATORS, new RoleSpecificationBuilder())
    );

    @Override
    public List<FilterableProperty<UserEntity>> getFilterableProperties() {
        return FILTERABLE_PROPERTIES;
    }
}