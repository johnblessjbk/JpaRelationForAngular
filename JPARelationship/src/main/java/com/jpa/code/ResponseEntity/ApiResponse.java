package com.jpa.code.ResponseEntity;

import lombok.Data;

@Data
public class ApiResponse<T> {
	private String status;
	private String message;
	private T data;
	public ApiResponse(String status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	// Getters and Setters
}