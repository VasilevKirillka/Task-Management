package com.example.Task_Management.service;

import com.example.Task_Management.dto.PageTaskResponse;
import com.example.Task_Management.dto.TaskDto;
import com.example.Task_Management.entity.UserEntity;

public interface TaskService {
    TaskDto createTask(TaskDto task, UserEntity user);
    TaskDto updateTask(Long id, TaskDto task, Long userId);

    TaskDto getTaskById(Long id);
    PageTaskResponse getAllUserTask(Long id, int page, int size);
    PageTaskResponse getTasksByAuthorId(Long authorId, int page, int size);
    PageTaskResponse getAllTaskByExecutorId(Long executorId, int page, int size);
    PageTaskResponse getAllTask(int page, int size);
    String deleteTask(Long id, Long userId);
}
