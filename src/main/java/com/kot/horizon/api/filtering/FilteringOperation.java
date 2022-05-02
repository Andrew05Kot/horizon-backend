package com.kot.horizon.api.filtering;

public enum FilteringOperation {

    EQUAL("="),
    NOT_EQUAL("!="),

    CONTAIN(":"),

    GREATER_THEN(">"),
    GREATER_OR_EQUAL(">="),

    LESS_THEN("<"),
    LESS_OR_EQUAL("<="),

    IN("_="),

    EMPTY("empty") ;

    private final String code;

    FilteringOperation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static FilteringOperation fromString(String text) {
        for (FilteringOperation b : FilteringOperation.values()) {
            if (b.code.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalFilteringOperationException("Unknown filtering operation '" + text + "'");
    }
}