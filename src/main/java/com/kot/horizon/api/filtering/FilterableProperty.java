package com.kot.horizon.api.filtering;

import java.util.List;
import com.kot.horizon.api.filtering.specifications.SpecificationBuilder;

public class FilterableProperty<T> {
    private String propertyName;
    private Class<?> expectedType;
    private List<FilteringOperation> operators;
    private SpecificationBuilder<T> specificationBuilder;

    public FilterableProperty(String propertyName, Class<?> expectedType, List<FilteringOperation> operators, SpecificationBuilder<T> specificationBuilder) {
        this.propertyName = propertyName;
        this.expectedType = expectedType;
        this.operators = operators;
        this.specificationBuilder = specificationBuilder;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class<?> getExpectedType() {
        return expectedType;
    }

    public List<FilteringOperation> getOperators() {
        return operators;
    }

    public SpecificationBuilder<T> getSpecificationBuilder() {
        return specificationBuilder;
    }
}
