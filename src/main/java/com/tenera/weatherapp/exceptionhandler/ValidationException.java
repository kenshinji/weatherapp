package com.tenera.weatherapp.exceptionhandler;

public class ValidationException extends RuntimeException {
    private String message;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
