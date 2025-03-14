package com.example.holyclick.church.exception;

public class MassLimitExceededException extends RuntimeException {
    public MassLimitExceededException(String message) {
        super(message);
    }
} 