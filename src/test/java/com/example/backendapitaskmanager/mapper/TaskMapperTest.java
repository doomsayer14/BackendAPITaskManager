package com.example.backendapitaskmanager.mapper;

import com.example.backendapitaskmanager.dto.TaskDto;
import com.example.backendapitaskmanager.entity.Task;
import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TaskMapperTest {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    void testJsScriptToDto() {
        // Given
        Task task = new Task();
        task.setId(1L);
        task.setName("Sample Task");
        task.setDescription("Sample Description");
        task.setTaskStatus(TaskStatus.WAITING);

        // When
        TaskDto taskDto = taskMapper.jsScriptToDto(task);

        // Then
        assertEquals(1L, taskDto.getId());
        assertEquals("Sample Task", taskDto.getName());
        assertEquals("Sample Description", taskDto.getDescription());
        assertEquals(TaskStatus.WAITING, taskDto.getTaskStatus());
    }
}

