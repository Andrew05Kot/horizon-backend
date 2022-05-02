package com.kot.horizon.exception;

public class EasyPayException extends RuntimeException {

	private String errorCode;

	public EasyPayException(String errorMessage) {
		super(errorMessage);
	}

	public EasyPayException(String errorMessage, String errorCode) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
