package com.example.Task_Management.exceptionHandler.exceptionDto;

public class PageValidateException extends RuntimeException{
    public PageValidateException(String message) {
        super(message);
    }
}
