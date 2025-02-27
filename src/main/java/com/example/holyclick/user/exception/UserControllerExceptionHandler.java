package com.example.holyclick.user.exception;

import com.example.holyclick.ErrorResponse;
import com.example.holyclick.ErrorResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.USER_ALREADY_EXIST, e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }

    @ExceptionHandler(PasswordNotValidException.class)
    public ResponseEntity<Object> handlePasswordNotValidException(PasswordNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.USER_PASSWORD_NOT_VALID, e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }

    @ExceptionHandler(PasswordsDoNotMatchException.class)
    public ResponseEntity<Object> handlePasswordsDoNotMatchException(PasswordsDoNotMatchException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorResponseCode.USER_PASSWORDS_DO_NOT_MATCH, e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }

    @ExceptionHandler(UsernameNotExistException.class)
    public ResponseEntity<Object> handleUsernameNotExistException(UsernameNotExistException e) {
        ErrorResponse errorResponse = new ErrorResponse((ErrorResponseCode.USER_WRONG_USERNAME), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handleWrongPasswordException(WrongPasswordException e) {
        ErrorResponse errorResponse = new ErrorResponse((ErrorResponseCode.USER_WRONG_PASSWORD), e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }
}