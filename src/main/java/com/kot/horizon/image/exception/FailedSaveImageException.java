package com.kot.horizon.image.exception;

public class FailedSaveImageException extends RuntimeException {

	public FailedSaveImageException() {
	}

	public FailedSaveImageException(String message) {
		super(message);
	}

	public FailedSaveImageException(String message, Throwable cause) {
		super(message, cause);
	}
}
