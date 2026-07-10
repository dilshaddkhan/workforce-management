package com.company.workforce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(
            EmployeeNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "NOT_FOUND",
                        ex.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(DuplicateEmployeeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmployee(
            DuplicateEmployeeException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Duplicate Employee",
                        ex.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "INTERNAL_SERVER_ERROR",
                        ex.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "BAD_REQUEST",
                        message,
                        request.getRequestURI()
                );

        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(
            UserAlreadyExistsException.class
    )
    public ResponseEntity<ErrorResponse>
    handleUserAlreadyExistsException(
            UserAlreadyExistsException ex,
            HttpServletRequest request
    ) {

        ErrorResponse response =
                ErrorResponse.builder()
                        .timestamp(
                                LocalDateTime.now()
                        )
                        .status(
                                HttpStatus.BAD_REQUEST.value()
                        )
                        .error(
                                HttpStatus.BAD_REQUEST.name()
                        )
                        .message(
                                ex.getMessage()
                        )
                        .path(
                                request.getRequestURI()
                        )
                        .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}