package com.company.workforce.dto;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
}