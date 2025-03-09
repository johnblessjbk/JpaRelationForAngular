package com.jpa.code.utility;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationUtil {
    public static ResponseEntity<?> validateRequest(BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(". ");
            }
            return ResponseEntity.badRequest().body("Validation errors occurred: " + errorMessage.toString());
        }
        return null; // No validation errors
    }
}
