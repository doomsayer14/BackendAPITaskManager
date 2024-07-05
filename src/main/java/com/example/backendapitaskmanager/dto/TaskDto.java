package com.example.backendapitaskmanager.dto;

import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private TaskStatus taskStatus;
}
