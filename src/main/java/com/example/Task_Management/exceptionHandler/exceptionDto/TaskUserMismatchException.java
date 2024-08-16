package com.example.Task_Management.exceptionHandler.exceptionDto;

public class TaskUserMismatchException extends RuntimeException{
    public TaskUserMismatchException(String message) {
        super(message);
    }
}
