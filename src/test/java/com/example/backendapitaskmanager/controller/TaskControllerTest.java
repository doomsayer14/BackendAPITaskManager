package com.example.backendapitaskmanager.controller;

import com.example.backendapitaskmanager.dto.TaskDto;
import com.example.backendapitaskmanager.entity.Task;
import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import com.example.backendapitaskmanager.mapper.TaskMapper;
import com.example.backendapitaskmanager.response.Response;
import com.example.backendapitaskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void createTask() {
        // Given
        Task task = new Task();
        Long taskId = 1L;
        when(taskService.createTask(any(Task.class))).thenReturn(taskId);

        // When
        Response response = taskController.createTask(task);

        // Then
        assertEquals("The task was successfully created with id = " + taskId, response.getMessage());
        verify(taskService).createTask(task);
    }

    @Test
    void deleteTask() {
        // Given
        Long taskId = 1L;

        // When
        Response response = taskController.deleteTask(taskId);

        // Then
        assertEquals("The task was successfully deleted", response.getMessage());
        verify(taskService).deleteTask(taskId);
    }

    @Test
    void updateTaskStatus() {
        // Given
        Long taskId = 1L;
        TaskStatus status = TaskStatus.COMPLETED;
        when(taskService.updateTaskStatus(taskId, status)).thenReturn(status);

        // When
        Response response = taskController.updateTaskStatus(taskId, status);

        // Then
        assertEquals("The task status was successfully changed to " + status, response.getMessage());
        verify(taskService).updateTaskStatus(taskId, status);
    }

    @Test
    void updateTask() {
        // Given
        Task task = new Task();

        // When
        Response response = taskController.updateTask(task);

        // Then
        assertEquals("The task fields was successfully changed", response.getMessage());
        verify(taskService).updateTask(task);
    }

    @Test
    void getAllTasks() {
        // Given
        TaskDto taskDto = new TaskDto();
        List<Task> tasks = List.of(new Task());
        when(taskService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.jsScriptToDto(any(Task.class))).thenReturn(taskDto);

        // When
        List<TaskDto> result = taskController.getAllTasks();

        // Then
        assertEquals(1, result.size());
        verify(taskService).getAllTasks();
        verify(taskMapper).jsScriptToDto(tasks.get(0));
    }
}
