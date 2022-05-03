package com.kot.horizon.api.v1;

import lombok.Value;

@Value
public class ApiResponse {
	private Boolean success;
	private String message;
}