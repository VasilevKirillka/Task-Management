package com.example.Task_Management.repository;

import com.example.Task_Management.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Page<TaskEntity> findAll(Pageable pageable);

    Page<TaskEntity> findAllByAuthorId(Long authorId, Pageable pageable);

    Page<TaskEntity> findAllByExecutorId(Long executorId, Pageable pageable);



}
