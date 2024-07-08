package com.example.backendapitaskmanager.mapper;

import com.example.backendapitaskmanager.dto.TaskDto;
import com.example.backendapitaskmanager.entity.Task;
import org.springframework.stereotype.Component;

/**
 * This class is needed to convert {@link Task} to {@link TaskDto}.
 */
@Component
public class TaskMapper {
    public TaskDto jsScriptToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .taskStatus(task.getTaskStatus())
                .build();
    }
}