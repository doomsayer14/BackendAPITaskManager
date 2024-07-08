package com.example.backendapitaskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The TaskNotFoundException can be thrown when some methods cannot find such
 * {@link com.example.backendapitaskmanager.entity.Task} in database.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException  {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
