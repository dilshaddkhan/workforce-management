package com.company.workforce.service.impl;

import com.company.workforce.constants.SortDirection;
import com.company.workforce.dto.EmployeeFilterRequest;
import com.company.workforce.dto.EmployeeRequest;
import com.company.workforce.dto.EmployeeResponse;
import com.company.workforce.dto.PageResponse;
import com.company.workforce.entity.Employee;
import com.company.workforce.exception.DuplicateEmployeeException;
import com.company.workforce.exception.EmployeeNotFoundException;
import com.company.workforce.mapper.EmployeeMapper;
import com.company.workforce.mapper.EmployeeMapperMAP;
import com.company.workforce.mapper.PageResponseMapper;
import com.company.workforce.repository.EmployeeRepository;
import com.company.workforce.service.EmployeeService;
import com.company.workforce.specification.EmployeeSpecificationBuilder;
import com.company.workforce.util.PaginationUtil;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl
        implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapperMAP employeeMapperMAP;
    private final EmployeeSpecificationBuilder specificationBuilder;
    private final PageResponseMapper pageResponseMapper;

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeMapperMAP employeeMapperMAP, EmployeeSpecificationBuilder specificationBuilder, PageResponseMapper pageResponseMapper) {
        this.repository = repository;
        this.employeeMapperMAP = employeeMapperMAP;
        this.specificationBuilder = specificationBuilder;
        this.pageResponseMapper = pageResponseMapper;
    }


    @PostConstruct
    public void init() {
        System.out.println(employeeMapperMAP.getClass());
    }

    @Override
    public EmployeeResponse createEmployee(
            EmployeeRequest request) {

        if (repository.existsByEmail(
                request.email())) {

            throw new DuplicateEmployeeException(
                    "Email already exists");
        }

        Employee employee =
                EmployeeMapper.toEntity(request);

        employee.setJoiningDate(LocalDate.now());
        employee.setActive(true);

        Employee saved =
                repository.save(employee);

/*
        return EmployeeMapper.toResponse(saved);
*/
        return employeeMapperMAP.toResponse(saved);

    }

    @Override
    public EmployeeResponse getEmployee(Long id) {

        /*Employee employee =
                repository.findById(id)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found"));
         return EmployeeMapper.toResponse(employee);*/
        return employeeMapperMAP.toResponse(repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found")));
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
/*
return repository.findAll().stream().filter((employee -> employee.getSalary().compareTo(BigDecimal.valueOf(1500000))>=0)).map(EmployeeMapper::toResponse).toList();
*/
        /*return repository.findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();*/

        return employeeMapperMAP.toResponseList(repository.findByActiveTrue());
    }

    @Override
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        Employee employee =
                repository.findById(id)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found"));

     /*employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setEmail(request.email());
        employee.setSalary(request.salary());

        return EmployeeMapper.toResponse(
                repository.save(employee));*/

        employeeMapperMAP.updateEmployeeFromRequest(
                request,
                employee
        );

        Employee updatedEmployee =
                repository.save(employee);

        return employeeMapperMAP.toResponse(updatedEmployee);

    }

  /*  @Override
    public void deleteEmployee(Long id) {

        Employee employee =
                repository.findById(id)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(
                                        "Employee not found"));

        repository.delete(employee);
    }*/

    @Override
    @Transactional
    public void deleteEmployee(Long id) {

        Employee employee =
                repository.findByIdAndActiveTrue(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Employee not found"
                                )
                        );

        employee.setActive(false);

        repository.save(employee);
    }

    @Override
    public List<EmployeeResponse> getAllEmployeesByFirstName(String firstName) {
    /*    return repository.findByFirstName(firstName)
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();*/
        return repository.findByFirstName(firstName)
                .stream()
                .map(employeeMapperMAP::toResponse)
                .toList();
    }

    @Override
    public List<EmployeeResponse> getAllEmployeesByFirstNameAndEmail(String firstName, String email) {
        /*return repository.findEmployee(firstName,email)
                .stream()
                .map(EmployeeMapper::toResponse)
                .toList();*/

        return repository.findEmployee(firstName, email)
                .stream()
                .map(employeeMapperMAP::toResponse)
                .toList();
    }

    @Override
    public PageResponse<EmployeeResponse> getEmployees(
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        size = PaginationUtil.normalizeSize(size);
        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Employee> employeePage =
                repository.findAll(pageable);

        List<EmployeeResponse> content =
                employeeMapperMAP.toResponseList(
                        employeePage.getContent()
                );

        return new PageResponse<>(
                content,
                employeePage.getNumber(),
                employeePage.getSize(),
                employeePage.getTotalElements(),
                employeePage.getTotalPages(),
                employeePage.isFirst(),
                employeePage.isLast()
        );
    }

    @Override
    public PageResponse<EmployeeResponse> searchEmployees(
            EmployeeFilterRequest request,
            int page,
            int size,
            String sortBy,
            SortDirection direction
    ) {

        Specification<Employee> specification =
                specificationBuilder.build(request);

        size = PaginationUtil.normalizeSize(size);

        Pageable pageable =
                PaginationUtil.buildPageable(
                        page,
                        size,
                        sortBy,
                        direction
                );

        Page<Employee> employeePage =
                repository.findAll(
                        specification,
                        pageable
                );

        List<EmployeeResponse> responses =
                employeeMapperMAP.toResponseList(
                        employeePage.getContent()
                );

        return pageResponseMapper.map(
                employeePage,
                responses
        );
    }

}