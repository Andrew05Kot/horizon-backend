package com.kot.horizon.service.facebook;

public enum FacebookField {
    PICTURE ("picture"),
    DATA ("data"),
    URL ("url"),
    CUSTOM_BIG_PICTURE ("picture.width(500).height(500)");

    private String fieldName;

    FacebookField(String fieldName) {
        this.fieldName = fieldName;
    }

    String getField() {
        return this.fieldName;
    }
}