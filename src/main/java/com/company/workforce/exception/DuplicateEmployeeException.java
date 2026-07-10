package com.company.workforce.exception;

public class DuplicateEmployeeException
        extends RuntimeException {

    public DuplicateEmployeeException(String message) {
        super(message);
    }
}