package com.example.backendapitaskmanager.controller;

import com.example.backendapitaskmanager.dto.TaskDto;
import com.example.backendapitaskmanager.entity.Task;
import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import com.example.backendapitaskmanager.mapper.TaskMapper;
import com.example.backendapitaskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testCreateTask() throws Exception {
        // Given
        Task task = new Task();
        task.setId(1L);
        when(taskService.createTask(any(Task.class))).thenReturn(1L);

        // When & Then
        mockMvc.perform(post("/api/taskmanager/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Sample Task\", \"description\": \"Sample Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("The task was successfully created with id = 1"))
                .andDo(print());
    }

    @Test
    void testDeleteTask() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/taskmanager/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void testUpdateTaskStatus() throws Exception {
        // Given
        when(taskService.updateTaskStatus(1L, TaskStatus.COMPLETED)).thenReturn(TaskStatus.COMPLETED);

        // When & Then
        mockMvc.perform(put("/api/taskmanager/1/COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("The task status was successfully changed to COMPLETED"))
                .andDo(print());
    }

    @Test
    void testUpdateTask() throws Exception {
        // When & Then
        mockMvc.perform(patch("/api/taskmanager/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"name\": \"Updated Task\", \"description\": \"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("The task fields was successfully changed"))
                .andDo(print());
        verify(taskService, times(1)).updateTask(any(Task.class));
    }

    @Test
    void testGetAllTasks() throws Exception {
        // Given
        Task task = new Task();
        task.setId(1L);
        TaskDto taskDto = TaskDto.builder()
                .id(1L)
                .name("Sample Task")
                .description("Sample Description")
                .build();
        when(taskService.getAllTasks()).thenReturn(List.of(task));
        when(taskMapper.jsScriptToDto(any(Task.class))).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(get("/api/taskmanager/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Sample Task"))
                .andExpect(jsonPath("$[0].description").value("Sample Description"))
                .andDo(print());
        verify(taskService, times(1)).getAllTasks();
        verify(taskMapper, times(1)).jsScriptToDto(any(Task.class));
    }
}
