package com.example.holyclick.persona.exception;

import com.example.holyclick.ErrorResponse;
import com.example.holyclick.ErrorResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PersonaControllerExceptionHandler {

    @ExceptionHandler(PersonaAlreadyExistsException.class)
    public ResponseEntity<Object> handlePersonaAlreadyExistsException(PersonaAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.PERSONA_ALREADY_EXISTS, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidAdminKeyException.class)
    public ResponseEntity<Object> handleInvalidAdminKeyException(InvalidAdminKeyException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.INVALID_ADMIN_KEY, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(PersonaNotFoundException.class)
    public ResponseEntity<Object> handlePersonaNotFoundException(PersonaNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.PERSONA_NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PersonaNotBelongToUserException.class)
    public ResponseEntity<Object> handlePersonaNotBelongToUserException(PersonaNotBelongToUserException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.PERSONA_NOT_BELONG_TO_USER, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(InvalidPersonaTypeException.class)
    public ResponseEntity<Object> handleInvalidPersonaTypeException(InvalidPersonaTypeException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.INVALID_PERSONA_TYPE, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedPersonaException.class)
    public ResponseEntity<Object> handleUnauthorizedPersonaException(UnauthorizedPersonaException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.UNAUTHORIZED_PERSONA, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.UNAUTHORIZED_PERSONA, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
} 