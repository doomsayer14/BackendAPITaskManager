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

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final AmqpTemplate amqpTemplate;

    private int taskAmount;

    @Value("${tasks.max.amount}")
    private int tasksMaxAmount;

    public TaskService(TaskRepository taskRepository, AmqpTemplate amqpTemplate) {
        this.taskRepository = taskRepository;
        this.amqpTemplate = amqpTemplate;
    }

    public Long createTask(Task task) {
        if (taskAmount > tasksMaxAmount) {
            logger.error("Task amount is more then {}", tasksMaxAmount);
            throw new TaskAmountOutOfBoundsException("Task amount is more then " + tasksMaxAmount + ". Please delete some tasks.");
        }
        Task newTask = taskRepository.save(task);
        taskAmount++;
        amqpTemplate.convertAndSend("tasks", "The task hase been created: " + newTask);
        return newTask.getId();
    }

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

    public TaskStatus updateTaskStatus(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setTaskStatus(status);
        Task updatedTask = taskRepository.save(task);
        logger.info("Task status has been updated to: {}, task id: {}", updatedTask.getTaskStatus(), updatedTask.getId());
        return updatedTask.getTaskStatus();
    }

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

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    private Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("The task cannot be found for id" + id));
    }
}
