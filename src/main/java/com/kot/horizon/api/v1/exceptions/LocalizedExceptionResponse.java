package com.kot.horizon.api.v1.exceptions;

public class LocalizedExceptionResponse {
	private String reason;
	private String message;

	public LocalizedExceptionResponse() {
	}

	public LocalizedExceptionResponse(String reason, String message) {
		this.reason = reason;
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public String getMessage() {
		return message;
	}
}
