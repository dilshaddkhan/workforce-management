package com.company.workforce.exception;

public class UserAlreadyExistsException
        extends RuntimeException {

    public UserAlreadyExistsException(
            String message
    ) {
        super(message);
    }
}