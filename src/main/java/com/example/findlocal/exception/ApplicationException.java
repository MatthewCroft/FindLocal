package com.example.findlocal.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    private HttpStatus httpStatus;
    public ApplicationException(String message, HttpStatus status) {
       super(message);
       this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
