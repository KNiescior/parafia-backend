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
    public ResponseEntity<ErrorResponse> handleChurchNotFoundException(ChurchNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.CHURCH_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChurchNotBelongToParishException.class)
    public ResponseEntity<ErrorResponse> handleChurchNotBelongToParishException(ChurchNotBelongToParishException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.CHURCH_NOT_BELONG_TO_PARISH, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ParishNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleParishNotFoundException(ParishNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.PARISH_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MassNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMassNotFoundException(MassNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.MASS_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedPersonaException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedPersonaException(UnauthorizedPersonaException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.UNAUTHORIZED_PERSONA, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.UNAUTHORIZED_PERSONA, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
} 