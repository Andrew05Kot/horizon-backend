package com.kot.horizon.common.exception;

public class LocalizedException extends RuntimeException {
	private String reason;
	private String contentKey;

	public LocalizedException(String reason, String contentKey) {
		this.reason = reason;
		this.contentKey = contentKey;
	}

	public String getReason() {
		return reason;
	}

	public String getContentKey() {
		return contentKey;
	}
}
