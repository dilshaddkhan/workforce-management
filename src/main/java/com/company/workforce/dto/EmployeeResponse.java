package com.company.workforce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeResponse(

        Long id,
        String firstName,
        String lastName,
        String email,
        BigDecimal salary,
        LocalDate joiningDate,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}