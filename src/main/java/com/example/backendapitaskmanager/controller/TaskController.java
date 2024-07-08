package com.example.backendapitaskmanager.controller;

import com.example.backendapitaskmanager.dto.TaskDto;
import com.example.backendapitaskmanager.entity.Task;
import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import com.example.backendapitaskmanager.mapper.TaskMapper;
import com.example.backendapitaskmanager.response.Response;
import com.example.backendapitaskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for {@link Task}. Provides create, delete, patch, put, get HTTP methods.
 */
@RestController
@RequestMapping("api/taskmanager")
public class TaskController {

    private final TaskMapper taskMapper;
    private final TaskService taskService;

    public TaskController(TaskMapper taskMapper, TaskService taskService) {
        this.taskMapper = taskMapper;
        this.taskService = taskService;
    }

    /**
     * Endpoint which can be called to create new Task.
     * @param task The task to be created.
     * @return {@link Response} with new id and 200 HTTP code.
     */
    @Operation(summary = "Create task", tags = "task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new task",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    })
    })
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createTask(@Valid @RequestBody Task task) {
        Long id = taskService.createTask(task);
        return new Response("The task was successfully created with id = " + id);
    }

    /**
     * Endpoint which can be called to delete Task.
     * @param id The task id to be deleted.
     * @return {@link Response} with confirmation of successful deletion and 204 HTTP code.
     */
    @Operation(summary = "Delete task", tags = "task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Delete task from database",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    })
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return new Response("The task was successfully deleted");
    }

    /**
     * Endpoint which can be called to update task status.
     * @param id The task id.
     * @param status New task status.
     * @return {@link Response} with new status and 200 HTTP code.
     */
    @Operation(summary = "Update task status", tags = "task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update task status",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    })
    })
    @PutMapping("/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateTaskStatus(@PathVariable Long id,
                                     @PathVariable TaskStatus status) {
        TaskStatus taskStatus = taskService.updateTaskStatus(id, status);
        return new Response("The task status was successfully changed to " + taskStatus);
    }

    /**
     * Endpoint which can be called to update Task.
     * @param task The task id to be updated.
     * @return {@link Response} with confirmation of successful update and 200 HTTP code.
     */
    @Operation(summary = "Update task", tags = "task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update task fields",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Response.class)))
                    })
    })
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateTask(@Valid @RequestBody Task task) {
        taskService.updateTask(task);
        return new Response("The task fields was successfully changed");
    }

    /**
     * Endpoint which can be called to get all Tasks.
     * @return {@link List} of all tasks and 200 HTTP code.
     */
    @Operation(summary = "Gets all tasks", tags = "task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the tasks",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = List.class)))
                    })
    })
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(taskMapper::jsScriptToDto)
                .toList();
    }
}