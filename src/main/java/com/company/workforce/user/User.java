package com.company.workforce.user;

import com.company.workforce.entity.BaseEntity;
import com.company.workforce.role.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String username;

    @Column(
            nullable = false,
            unique = true
    )
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(
            EnumType.STRING
    )
    @Column(nullable = false)
    private RoleType role;

    @Builder.Default
    private Boolean active = true;
}
