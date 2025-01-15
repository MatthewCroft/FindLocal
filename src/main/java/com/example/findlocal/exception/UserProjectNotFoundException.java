package com.example.findlocal.exception;

import org.springframework.http.HttpStatus;

public class UserProjectNotFoundException extends ApplicationException {
    public UserProjectNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
