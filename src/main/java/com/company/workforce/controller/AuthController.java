package com.company.workforce.controller;

import com.company.workforce.dto.*;
import com.company.workforce.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(

            @Valid
            @RequestBody
            RegisterRequest request
    ) {

        return new ApiResponse<>(
                true,
                "Registration successful",
                authService.register(
                        request
                )
        );
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(

            @Valid
            @RequestBody
            LoginRequest request
    ) {

        return new ApiResponse<>(

                true,

                "Login successful",

                authService.login(
                        request
                )
        );
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<UserProfileResponse> me() {

        System.out.println(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );
        return new ApiResponse<>(
                true,
                "User fetched successfully",
                authService.getCurrentUser()
        );
    }

}