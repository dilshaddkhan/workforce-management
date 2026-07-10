package com.company.workforce.mapper;

import com.company.workforce.dto.EmployeeRequest;
import com.company.workforce.dto.EmployeeResponse;
import com.company.workforce.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapperMAP
{

    Employee toEntity(EmployeeRequest request);

    EmployeeResponse toResponse(Employee employee);

    List<EmployeeResponse> toResponseList(List<Employee> employees);

    void updateEmployeeFromRequest(
            EmployeeRequest request,
            @MappingTarget Employee employee
    );
}
