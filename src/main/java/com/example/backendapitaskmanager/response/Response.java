package com.example.backendapitaskmanager.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Class for HTTP responses.
 */
@Data
@Builder
@AllArgsConstructor
public class Response {
    private String message;
}
