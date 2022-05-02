package com.kot.horizon.api.v1.exceptions;

public class ExceptionsMessageWrapper {

	private String reason;

	public ExceptionsMessageWrapper() {
	}

	public ExceptionsMessageWrapper(String messages) {
		this.reason = messages;
	}

	public String getReason() {
		return reason;
	}

}
