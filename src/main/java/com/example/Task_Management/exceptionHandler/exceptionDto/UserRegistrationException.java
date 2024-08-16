package com.example.Task_Management.exceptionHandler.exceptionDto;

public class UserRegistrationException extends RuntimeException{
    public UserRegistrationException(String message) {
        super(message);
    }
}
