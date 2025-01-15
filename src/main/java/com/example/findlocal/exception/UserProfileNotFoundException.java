package com.example.findlocal.exception;

import org.springframework.http.HttpStatus;

public class UserProfileNotFoundException extends ApplicationException {
    public UserProfileNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
