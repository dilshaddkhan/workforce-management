package com.company.workforce.service;

import com.company.workforce.dto.*;

public interface AuthService {

    AuthResponse register(
            RegisterRequest request
    );

    LoginResponse login(
            LoginRequest request
    );

    public UserProfileResponse getCurrentUser();
}