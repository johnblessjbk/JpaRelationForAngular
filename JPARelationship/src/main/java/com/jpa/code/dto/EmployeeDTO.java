package com.jpa.code.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id; // Optional: Include if needed in API responses

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 15, message = "Minimum char should be 3 and Max is 15")
    private String name;

    @NotEmpty(message = "Employee Id should not be empty")
    private String empid;

    @NotEmpty(message = "Job Role should not be empty")
    private String jobrole;
}