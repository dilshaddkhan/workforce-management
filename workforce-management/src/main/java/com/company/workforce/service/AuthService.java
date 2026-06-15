package com.company.workforce.service;

import com.company.workforce.dto.AuthResponse;
import com.company.workforce.dto.LoginRequest;
import com.company.workforce.dto.LoginResponse;
import com.company.workforce.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(
            RegisterRequest request
    );

    LoginResponse login(
            LoginRequest request
    );
}