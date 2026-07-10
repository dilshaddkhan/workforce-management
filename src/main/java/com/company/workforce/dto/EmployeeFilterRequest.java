package com.company.workforce.dto;

import java.math.BigDecimal;

public record EmployeeFilterRequest(

        String firstName,

        String lastName,

        String email,

        BigDecimal minSalary,

        BigDecimal maxSalary,

        Boolean active

) {
}