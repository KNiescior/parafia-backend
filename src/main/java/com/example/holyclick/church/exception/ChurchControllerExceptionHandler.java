package com.example.holyclick.church.exception;

import com.example.holyclick.ErrorResponse;
import com.example.holyclick.ErrorResponseCode;
import com.example.holyclick.persona.exception.UnauthorizedPersonaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ChurchControllerExceptionHandler {

    @ExceptionHandler(ChurchNotFoundException.class)
    public ResponseEntity<Object> handleChurchNotFoundException(ChurchNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.CHURCH_NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ChurchNotBelongToParishException.class)
    public ResponseEntity<Object> handleChurchNotBelongToParishException(ChurchNotBelongToParishException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.CHURCH_NOT_BELONG_TO_PARISH, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(ParishNotFoundException.class)
    public ResponseEntity<Object> handleParishNotFoundException(ParishNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.PARISH_NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedPersonaException.class)
    public ResponseEntity<Object> handleUnauthorizedPersonaException(UnauthorizedPersonaException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.UNAUTHORIZED_PERSONA, 
            "Only RECTOR persona can manage churches. " + e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.UNAUTHORIZED_PERSONA, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
} 