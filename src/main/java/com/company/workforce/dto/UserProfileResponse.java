package com.company.workforce.dto;

public record UserProfileResponse(

        Long id,

        String username,

        String email,

        String role
) {
}