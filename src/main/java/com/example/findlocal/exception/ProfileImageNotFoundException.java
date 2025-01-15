package com.example.findlocal.exception;

import org.springframework.http.HttpStatus;

public class ProfileImageNotFoundException extends ApplicationException {
    public ProfileImageNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
