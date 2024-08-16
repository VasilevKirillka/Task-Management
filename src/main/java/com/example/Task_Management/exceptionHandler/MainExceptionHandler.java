package com.example.Task_Management.exceptionHandler;

import com.example.Task_Management.exceptionHandler.exceptionDto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class MainExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<ValidationErrorResponse> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> new ValidationErrorResponse(
            error.getField(), error.getDefaultMessage())
        ).collect(Collectors.toList());
        log.error(String.valueOf(errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedExceptionResponse.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedExceptionResponse ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UsernameNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(UserRegistrationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskUserMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTaskUserMismatchException(TaskUserMismatchException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PageValidateException.class)
    public ResponseEntity<ErrorResponse> handlePageValidateException(PageValidateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
