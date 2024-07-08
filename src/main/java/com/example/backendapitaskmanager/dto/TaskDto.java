package com.example.backendapitaskmanager.dto;

import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for {@link com.example.backendapitaskmanager.entity.Task} class.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private TaskStatus taskStatus;
}
