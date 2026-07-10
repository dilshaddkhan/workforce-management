package com.company.workforce.service;

import com.company.workforce.constants.SortDirection;
import com.company.workforce.dto.*;
import com.company.workforce.entity.Employee;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    EmployeeResponse createEmployee(
            EmployeeRequest request);

    EmployeeResponse getEmployee(Long id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request);

    void deleteEmployee(Long id);

    List<EmployeeResponse> getAllEmployeesByFirstName(String firstName);

    List<EmployeeResponse> getAllEmployeesByFirstNameAndEmail(String firstName,
           String email
    );

    PageResponse<EmployeeResponse> getEmployees(
            int page,
            int size,
            String sortBy,
            String sortDirection
    );

    PageResponse<EmployeeResponse> searchEmployees(
            EmployeeFilterRequest request,
            int page,
            int size,
            String sortBy,
            SortDirection direction
    );
}