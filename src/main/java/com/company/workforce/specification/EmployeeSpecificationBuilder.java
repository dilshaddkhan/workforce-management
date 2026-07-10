package com.company.workforce.specification;

import com.company.workforce.dto.EmployeeFilterRequest;
import com.company.workforce.entity.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSpecificationBuilder {

    public Specification<Employee> build(
            EmployeeFilterRequest request
    ) {

        Specification<Employee> specification =
                (root, query, cb) -> cb.conjunction();

        if(request.active() != null) {

            specification =
                    specification.and(
                            (root, query, cb) ->
                                    cb.equal(
                                            root.get("active"),
                                            request.active()
                                    )
                    );
        }
        if (hasText(request.firstName())) {
            specification = specification.and(
                    EmployeeSpecification.hasFirstName(
                            request.firstName()
                    )
            );
        }

        if (hasText(request.lastName())) {
            specification = specification.and(
                    EmployeeSpecification.hasLastName(
                            request.lastName()
                    )
            );
        }

        if (hasText(request.email())) {
            specification = specification.and(
                    EmployeeSpecification.hasEmail(
                            request.email()
                    )
            );
        }

        if (request.minSalary() != null) {
            specification = specification.and(
                    EmployeeSpecification.salaryGreaterThan(
                            request.minSalary()
                    )
            );
        }

        if (request.maxSalary() != null) {
            specification = specification.and(
                    EmployeeSpecification.salaryLessThan(
                            request.maxSalary()
                    )
            );
        }

        return specification;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}