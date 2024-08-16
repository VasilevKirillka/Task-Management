package com.example.Task_Management.exceptionHandler.exceptionDto;

public class UnauthorizedExceptionResponse extends RuntimeException{
    public UnauthorizedExceptionResponse(String message) {
        super(message);
    }
}
