package com.company.workforce.specification;

import com.company.workforce.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public final class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> hasFirstName(
            String firstName
    ) {

        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("firstName")),
                        "%" + firstName.toLowerCase() + "%"
                );
    }

    public static Specification<Employee> hasLastName(
            String lastName
    ) {

        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("lastName")),
                        "%" + lastName.toLowerCase() + "%"
                );
    }

    public static Specification<Employee> hasEmail(
            String email
    ) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("email"),
                        email
                );
    }

    public static Specification<Employee> isActive(
            Boolean active
    ) {

        return (root, query, cb) ->
                cb.equal(
                        root.get("active"),
                        active
                );
    }

    public static Specification<Employee> salaryGreaterThan(
            BigDecimal salary
    ) {

        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(
                        root.get("salary"),
                        salary
                );
    }

    public static Specification<Employee> salaryLessThan(
            BigDecimal salary
    ) {

        return (root, query, cb) ->
                cb.lessThanOrEqualTo(
                        root.get("salary"),
                        salary
                );
    }
}