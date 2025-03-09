package com.jpa.code.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<?> handleDuplicateUserDta(Exception ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		StringBuilder errorMessage = new StringBuilder();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errorMessage.append(error.getDefaultMessage()).append(". ");
		}
		return ResponseEntity.badRequest().body("Validation errors occurred: " + errorMessage.toString());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
		StringBuilder errorMessage = new StringBuilder();
		ex.getConstraintViolations().forEach(violation -> {
			errorMessage.append(violation.getMessage()).append(". ");
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<?> handleGenericException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}

}
