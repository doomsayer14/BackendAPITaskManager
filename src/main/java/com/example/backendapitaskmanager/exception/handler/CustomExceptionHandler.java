package com.example.backendapitaskmanager.exception.handler;

import com.example.backendapitaskmanager.exception.TaskAmountOutOfBoundsException;
import com.example.backendapitaskmanager.exception.TaskNotFoundException;
import com.example.backendapitaskmanager.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleInternalServerException(TaskNotFoundException e) {
        return Response.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = TaskAmountOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleInternalServerException(TaskAmountOutOfBoundsException e) {
        return Response.builder()
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new Response(errors.toString());
    }
}
