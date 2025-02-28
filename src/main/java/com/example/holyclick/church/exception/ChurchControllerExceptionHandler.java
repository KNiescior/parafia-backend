package com.example.holyclick.church.exception;

import com.example.holyclick.ErrorResponse;
import com.example.holyclick.ErrorResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ChurchControllerExceptionHandler {

    @ExceptionHandler(ParishAlreadyExistsException.class)
    public ResponseEntity<Object> handleParishAlreadyExistsException(ParishAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.PARISH_ALREADY_EXISTS, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
} 