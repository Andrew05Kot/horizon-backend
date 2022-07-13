package com.kot.horizon.common.filtering;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = 7860216991885895782L;

    private final String key;
    private final FilteringOperation operation;
    private final Object value;

    public SearchCriteria(String key, FilteringOperation operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public FilteringOperation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getType() {
        return value == null ? null : value.getClass();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getKey());
        builder.append(getOperation());
        builder.append(getValue());
        return builder.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SearchCriteria)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        final SearchCriteria otherObject = (SearchCriteria) o;
        return new EqualsBuilder()
                .append(this.key, otherObject.key)
                .append(this.operation, otherObject.operation)
                .append(this.value, otherObject.value)
                .isEquals();
    }

    @Override
    public String toString() {
        return "SearchCriteria[" + "key='" + key + '\'' + ", operation='" + operation + '\'' + ", value=" + value + ']';
    }
}