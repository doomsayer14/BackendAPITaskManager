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

/**
 * The class for handling exceptions.
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handles {@link TaskNotFoundException}
     * @param e TaskNotFoundException
     * @return {@link Response} class with 404 HTTP code.
     */
    @ExceptionHandler(value = TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleInternalServerException(TaskNotFoundException e) {
        return Response.builder()
                .message(e.getMessage())
                .build();
    }

    /**
     * Handles {@link TaskAmountOutOfBoundsException}
     * @param e TaskAmountOutOfBoundsException
     * @return {@link Response} class with 400 HTTP code.
     */
    @ExceptionHandler(value = TaskAmountOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleInternalServerException(TaskAmountOutOfBoundsException e) {
        return Response.builder()
                .message(e.getMessage())
                .build();
    }

    /**
     * Handles {@link MethodArgumentNotValidException} which can be thrown while spring validation.
     * @param e MethodArgumentNotValidException
     * @return {@link Response} class with 400 HTTP code.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationExceptions(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new Response(errors.toString());
    }

    /**
     * Handles any unexpected Java Core exceptions, like IOException, InterruptedException etc.
     * @param e Exception
     * @return {@link Response} class with 500 HTTP code.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        return Response.builder()
                .message(e.getMessage())
                .build();
    }
}
