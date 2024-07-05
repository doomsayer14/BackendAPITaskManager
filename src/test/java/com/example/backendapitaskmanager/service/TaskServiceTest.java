package com.example.backendapitaskmanager.service;

import com.example.backendapitaskmanager.entity.Task;
import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import com.example.backendapitaskmanager.exception.TaskAmountOutOfBoundsException;
import com.example.backendapitaskmanager.exception.TaskNotFoundException;
import com.example.backendapitaskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AmqpTemplate amqpTemplate;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask_Success() {
        // Given
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // When
        Long taskId = taskService.createTask(task);

        // Then
        assertEquals(1L, taskId);
        verify(taskRepository, times(1)).save(task);
        verify(amqpTemplate, times(1)).convertAndSend(eq("tasks"), anyString());
    }

    @Test
    void testCreateTask_TaskAmountExceedsMax() {
        // Given
        Task task = new Task();
        ReflectionTestUtils.setField(taskService, "taskAmount", 6); // Simulate task amount exceeding max

        // When & Then
        assertThrows(TaskAmountOutOfBoundsException.class, () -> taskService.createTask(task));
    }

    @Test
    void testDeleteTask_Success() {
        // Given
        ReflectionTestUtils.setField(taskService, "taskAmount", 1); // Simulate existing task amount

        // When
        taskService.deleteTask(1L);

        // Then
        verify(taskRepository, times(1)).deleteById(1L);
        assertEquals(0, ReflectionTestUtils.getField(taskService, "taskAmount"));
    }

    @Test
    void testDeleteTask_NothingToDelete() {

        // Given
        ReflectionTestUtils.setField(taskService, "taskAmount", 0);

        // When
        taskService.deleteTask(1L);

        // Then
        verify(taskRepository, times(1)).deleteById(1L);
        assertEquals(0, ReflectionTestUtils.getField(taskService, "taskAmount"));
    }

    @Test
    void testUpdateTaskStatus_Success() {
        // Given
        Task task = new Task();
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // When
        TaskStatus newStatus = TaskStatus.COMPLETED; // Assuming TaskStatus is an enum
        TaskStatus updatedStatus = taskService.updateTaskStatus(1L, newStatus);

        // Then
        assertEquals(newStatus, updatedStatus);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask_Success() {
        // Given
        Task existingTask = new Task();
        existingTask.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setName("Updated Name");

        // When
        taskService.updateTask(updatedTask);

        // Then
        verify(taskRepository, times(1)).save(existingTask);
        assertEquals("Updated Name", existingTask.getName());
    }

    @Test
    void testGetAllTasks_Success() {
        // Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        // When
        List<Task> result = taskService.getAllTasks();

        // Then
        assertEquals(1, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById_NotFound() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class,
                () -> ReflectionTestUtils.invokeMethod(taskService, "getTaskById", 1L));
    }
}

