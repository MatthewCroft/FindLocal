package com.example.findlocal.exception;

import org.springframework.http.HttpStatus;

public class UserValidationException extends ApplicationException {
    public UserValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
