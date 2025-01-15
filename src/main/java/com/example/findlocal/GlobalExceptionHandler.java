package com.example.findlocal;

import com.example.findlocal.exception.ApplicationException;
import com.example.findlocal.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException applicationException) {
        ErrorResponse errorResponse =
                new ErrorResponse(String.valueOf(applicationException.getHttpStatus().value()), applicationException.getMessage());

        return new ResponseEntity<>(errorResponse, applicationException.getHttpStatus());
    }
}
