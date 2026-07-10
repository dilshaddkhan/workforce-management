package com.company.workforce.mapper;

import com.company.workforce.dto.EmployeeRequest;
import com.company.workforce.dto.EmployeeResponse;
import com.company.workforce.entity.Employee;

public final class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static Employee toEntity(
            EmployeeRequest request) {

        return Employee.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .salary(request.salary())
                .build();
    }

    public static EmployeeResponse toResponse(
            Employee employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getJoiningDate(),
                employee.getActive(),
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }
}