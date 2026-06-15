package com.company.workforce.controller;

import com.company.workforce.constants.PaginationConstants;
import com.company.workforce.constants.SortDirection;
import com.company.workforce.dto.*;
import com.company.workforce.service.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Validated
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        return new ApiResponse<>(
                true,
                "Employee created successfully",
                employeeService.createEmployee(request)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponse> getEmployee(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Employee fetched successfully",
                employeeService.getEmployee(id)
        );
    }

    @GetMapping
    public ApiResponse<List<EmployeeResponse>> getAllEmployees() {

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                employeeService.getAllEmployees()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return new ApiResponse<>(
                true,
                "Employee updated successfully",
                employeeService.updateEmployee(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return new ApiResponse<>(
                true,
                "Employee deleted successfully",
                null
        );
    }

    @PostMapping("/searchByName")
    public ApiResponse<List<EmployeeResponse>> getAllEmployeesByFirstName(@Valid @RequestBody EmployeeSearchRequest employeeSearchRequest) {

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                employeeService.getAllEmployeesByFirstName(employeeSearchRequest.firstName())
        );
    }

    @PostMapping("/searchByNameAndEmail")
    public ApiResponse<List<EmployeeResponse>> getAEmployeeByNameAndEmail(@Valid @RequestBody EmployeeSearchRequest employeeSearchRequest) {

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                employeeService.getAllEmployeesByFirstNameAndEmail(employeeSearchRequest.firstName(),employeeSearchRequest.email())
        );
    }

    @GetMapping("/page")
    public ApiResponse<PageResponse<EmployeeResponse>>
    getEmployees(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDirection
    ) {

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                employeeService.getEmployees(
                        page,
                        size,
                        sortBy,
                        sortDirection
                )
        );
    }


    @PostMapping("/search")
    public ApiResponse<PageResponse<EmployeeResponse>>
    searchEmployees(
            @Valid
            @RequestBody
            EmployeeFilterRequest request,

            @RequestParam(
                    defaultValue = PaginationConstants.DEFAULT_PAGE_STRING
            )
            @Min(0)
            int page,

            @RequestParam(
                    defaultValue = PaginationConstants.DEFAULT_SIZE_STRING
            )
            @Min(1)
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "ASC")
            SortDirection direction
    ) {

        return new ApiResponse<>(
                true,
                "Employees fetched successfully",
                employeeService.searchEmployees(
                        request,
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
}