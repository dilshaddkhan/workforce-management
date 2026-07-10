package com.company.workforce.service.impl;

import com.company.workforce.dto.*;
import com.company.workforce.exception.UserAlreadyExistsException;
import com.company.workforce.repository.UserRepository;
import com.company.workforce.role.RoleType;
import com.company.workforce.security.JwtService;
import com.company.workforce.service.AuthService;
import com.company.workforce.user.User;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(
            RegisterRequest request
    ) {

        if(userRepository.existsByUsername(
                request.username()
        )) {

            throw new UserAlreadyExistsException(
                    "Username already exists"
            );
        }

        if(userRepository.existsByEmail(
                request.email()
        )) {

            throw new UserAlreadyExistsException(
                    "Email already exists"
            );
        }

        String encodedPassword =
                passwordEncoder.encode(
                        request.password()
                );

        System.out.println(
                "Encoded Password = " + encodedPassword
        );


        User user =
                User.builder()
                        .username(
                                request.username()
                        )
                        .email(
                                request.email()
                        )
                        .password(
                                passwordEncoder.encode(
                                        request.password()
                                )
                        )
                        .role(
                                RoleType.EMPLOYEE
                        )
                        .build();

        userRepository.save(user);

        return new AuthResponse(
                null,
                "User registered successfully"
        );
    }

    @Override
    public LoginResponse login(
            LoginRequest request
    ) {
        User user =
                userRepository
                        .findByUsername(request.username())
                        .orElseThrow();

        System.out.println("Raw Password = " + request.password());
        System.out.println("DB Password  = " + user.getPassword());

        System.out.println(
                passwordEncoder.matches(
                        request.password(),
                        user.getPassword()
                )
        );

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        String token =
                jwtService.generateToken(
                        request.username()
                );

        return new LoginResponse(
                token,
                "Bearer"
        );
    }

    @Override
    public UserProfileResponse getCurrentUser() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}