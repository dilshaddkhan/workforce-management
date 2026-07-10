package com.company.workforce.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@Builder
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private BigDecimal salary;

    private LocalDate joiningDate;

    @Builder.Default
    private Boolean active = true;

    public Employee(Long id, String firstName, String lastName, String email, BigDecimal salary, LocalDate joiningDate, Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.active = active;
    }

    public Employee() {
    }
}