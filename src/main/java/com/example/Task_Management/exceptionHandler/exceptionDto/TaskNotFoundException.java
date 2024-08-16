package com.example.Task_Management.exceptionHandler.exceptionDto;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
