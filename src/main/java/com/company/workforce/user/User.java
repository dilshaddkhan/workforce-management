package com.company.workforce.user;

import com.company.workforce.entity.BaseEntity;
import com.company.workforce.role.RoleType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
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

    public User(Long id, String username, String email, String password, RoleType role, Boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User() {
    }
}
