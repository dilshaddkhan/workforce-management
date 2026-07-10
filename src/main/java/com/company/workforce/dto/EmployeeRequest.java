package com.company.workforce.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EmployeeRequest(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @Email(message = "Invalid email")
        String email,

        @Positive(message = "Salary must be positive")
        BigDecimal salary

) {}