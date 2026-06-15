package com.company.workforce.repository;

import com.company.workforce.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee> {

    List<Employee> findByActiveTrue();

    Optional<Employee> findByIdAndActiveTrue(
            Long id
    );

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByFirstName(String firstName);

    boolean existsByEmail(String email);

    @Query("""
            SELECT e
            FROM Employee e
            WHERE e.firstName = :firstName
            AND e.email = :email
            """)
    Optional<Employee> findEmployee(
            @Param("firstName") String firstName,
            @Param("email") String email
    );
}