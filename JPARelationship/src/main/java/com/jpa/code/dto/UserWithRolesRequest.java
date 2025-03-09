package com.jpa.code.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserWithRolesRequest {
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Phone cannot be empty")
    private String phone;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Role IDs cannot be empty")
    private List<Long> roleIds;
}
