package com.example.backendapitaskmanager.repository;

import com.example.backendapitaskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository {@link JpaRepository} interface for {@link Task} class.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
