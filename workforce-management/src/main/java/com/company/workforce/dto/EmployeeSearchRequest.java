package com.company.workforce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record EmployeeSearchRequest(

        @Pattern(
                regexp = "^[a-zA-Z\\s'-]+$",
                message = "Only alphabets allowed"
        )
        String firstName,

        @Email(message = "Invalid email")
        String email

) {}