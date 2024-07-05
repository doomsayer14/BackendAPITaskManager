package com.example.backendapitaskmanager.controller;

import com.example.backendapitaskmanager.dto.TaskDto;
import com.example.backendapitaskmanager.entity.Task;
import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import com.example.backendapitaskmanager.mapper.TaskMapper;
import com.example.backendapitaskmanager.response.Response;
import com.example.backendapitaskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/taskmanager")
public class TaskController {

    private final TaskMapper taskMapper;
    private final TaskService taskService;

    public TaskController(TaskMapper taskMapper, TaskService taskService) {
        this.taskMapper = taskMapper;
        this.taskService = taskService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createTask(@Valid @RequestBody Task task) {
        Long id = taskService.createTask(task);
        return new Response("The task was successfully created with id = " + id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new Response("The task was successfully deleted");
    }

    @PutMapping("/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateTaskStatus(@PathVariable Long id,
                              @PathVariable TaskStatus status) {
        TaskStatus taskStatus = taskService.updateTaskStatus(id, status);
        return new Response("The task status was successfully changed to " + taskStatus);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateTask(@Valid @RequestBody Task task) {
        taskService.updateTask(task);
        return new Response("The task fields was successfully changed");
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(taskMapper::jsScriptToDto)
                .toList();
    }
}