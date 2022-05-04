package com.kot.horizon.common.filtering;

public class IllegalFilteringOperationException extends RuntimeException {

    private static final long serialVersionUID = 744571328521036919L;

    private String operation;

    public IllegalFilteringOperationException(String message) {
        super(message);
    }

    public IllegalFilteringOperationException(String operation, String message) {
        super(message);
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}