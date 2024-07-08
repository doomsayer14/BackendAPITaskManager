package com.example.backendapitaskmanager.service;

import com.example.backendapitaskmanager.entity.Task;
import com.example.backendapitaskmanager.entity.enums.TaskStatus;
import com.example.backendapitaskmanager.exception.TaskAmountOutOfBoundsException;
import com.example.backendapitaskmanager.exception.TaskNotFoundException;
import com.example.backendapitaskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for {@link Task}.
 * Contains business logic realization for {@link com.example.backendapitaskmanager.controller.TaskController}.
 */
@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final AmqpTemplate amqpTemplate;

    /**
     * Current task amount in app. We can do it volatile and use locks to implement multithreading
     * but there was no such requirements.
     */
    private int taskAmount;

    /**
     * Maximum task amount in app.
     */
    @Value("${task.max.amount}")
    private int taskMaxAmount;

    public TaskService(TaskRepository taskRepository, AmqpTemplate amqpTemplate) {
        this.taskRepository = taskRepository;
        this.amqpTemplate = amqpTemplate;
    }

    /**
     * Creates new task.
     * @param task Task to be created.
     * @return id of the new task.
     */
    public Long createTask(Task task) {
        if (taskAmount > taskMaxAmount) {
            logger.error("Task amount is more then {}", taskMaxAmount);
            throw new TaskAmountOutOfBoundsException("Task amount is more then " + taskMaxAmount + ". Please delete some tasks.");
        }
        Task newTask = taskRepository.save(task);
        taskAmount++;
        logger.info("Task amount is {}", taskAmount);
        amqpTemplate.convertAndSend("tasks", "The task hase been created: " + newTask);
        return newTask.getId();
    }

    /**
     * Deletes task
     * @param id id of the task to be deleted.
     */
    public void deleteTask(Long id) {
        if (taskAmount <= 0) {
            logger.info("Nothing to delete");
            taskRepository.deleteById(id);
            return;
        }
        taskRepository.deleteById(id);
        taskAmount--;
        logger.info("The task has been deleted with id: {}", id);
    }

    /**
     * Updates task status
     * @param id Task id
     * @param status New task status.
     * @return new task status.
     */
    public TaskStatus updateTaskStatus(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setTaskStatus(status);
        Task updatedTask = taskRepository.save(task);
        logger.info("Task status has been updated to: {}, task id: {}", updatedTask.getTaskStatus(), updatedTask.getId());
        return updatedTask.getTaskStatus();
    }

    /**
     * Updates task fields.
     * @param task Task with new fields.
     */
    public void updateTask(Task task) {
        Task newTask = getTaskById(task.getId());
        if (task.getTaskStatus() != null) {
            newTask.setTaskStatus(task.getTaskStatus());
        }
        if (task.getName() != null) {
            newTask.setName(task.getName());
        }
        if (task.getDescription() != null) {
            newTask.setDescription(task.getDescription());
        }
        taskRepository.save(newTask);
        logger.info("Task has been updated: {}", newTask);
    }

    /**
     * Gets all tasks.
     * @return all existing tasks.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Internal method to find task by id.
     * @param id Task id
     * @return Task
     * @throws {@link TaskNotFoundException} in case when there is no existing task with provided id.
     */
    private Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("The task cannot be found for id" + id));
    }
}
