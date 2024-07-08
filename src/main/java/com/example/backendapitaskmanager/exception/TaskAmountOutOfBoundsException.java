package com.example.backendapitaskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The TaskAmountOutOfBoundsException can be thrown when taskAmount from
 * {@link com.example.backendapitaskmanager.service.TaskService} is out of limit.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskAmountOutOfBoundsException extends RuntimeException {
    public TaskAmountOutOfBoundsException(String message) {
        super(message);
    }
}
