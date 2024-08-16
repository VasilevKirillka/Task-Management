package com.example.Task_Management.service;

import com.example.Task_Management.dto.PageTaskResponse;
import com.example.Task_Management.dto.TaskDto;
import com.example.Task_Management.entity.TaskEntity;
import com.example.Task_Management.entity.UserEntity;
import com.example.Task_Management.exceptionHandler.exceptionDto.TaskNotFoundException;
import com.example.Task_Management.exceptionHandler.exceptionDto.TaskUserMismatchException;
import com.example.Task_Management.mapper.TaskMapper;
import com.example.Task_Management.repository.TaskRepository;
import com.example.Task_Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDto createTask(TaskDto task, UserEntity user) {
        TaskEntity entity = createTaskEntity(task, user);
        log.info("Задача создана");
        return taskMapper.entityToTaskDto(entity);
    }

    public TaskDto updateTask(Long id, TaskDto task, Long userId) {
        TaskEntity taskEntity = getTaskEntityByTaskId(id);
        boolean isAuthor = taskEntity.getAuthor().getId().equals(userId);
        boolean isExecutor = taskEntity.getExecutor().getId().equals(userId);
        if (isAuthor) {
            taskEntity.setTitle(task.getTitle());
            taskEntity.setDescription(task.getDescription());
            taskEntity.setTaskStatus(task.getTaskStatus());
            taskEntity.setPriority(task.getPriority());
            taskEntity.setExecutor(getUserEntity(task.getTaskExecutorId()));
            log.info("Задача изменена автором");
        } else if (isExecutor) {
            log.info("Статус задачи изменен исполнителем");
            taskEntity.setTaskStatus(task.getTaskStatus());
        } else {
            throw new TaskUserMismatchException("Задача не изменена." +
                    " Пользователь не является автором или исполнителем задачи");
        }
        TaskEntity updateEntity = taskRepository.save(taskEntity);
        return taskMapper.entityToTaskDto(updateEntity);
    }

    public TaskDto getTaskById(Long id) {
        TaskDto task = taskMapper.entityToTaskDto(getTaskEntityByTaskId(id));
        log.info("Найдена задача с id: {}", id);
        return task;
    }

    public PageTaskResponse getAllUserTask(Long id, int page, int size) {
        int pageNumber = page - 1;
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<TaskEntity> taskPage = taskRepository.findAllByAuthorId(id, pageable);
        log.info("Выведен список задач текущего пользователя");
        return getPageTaskResponse(taskPage);
    }


    public PageTaskResponse getTasksByAuthorId(Long authorId, int page, int size) {
        getUserEntity(authorId);
        int pageNumber = page - 1;
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<TaskEntity> taskPage = taskRepository.findAllByAuthorId(authorId, pageable);
        log.info("Выведен список задач по id автора = {}", authorId);
        return getPageTaskResponse(taskPage);

    }

    public PageTaskResponse getAllTaskByExecutorId(Long executorId, int page, int size) {
        getUserEntity(executorId);
        int pageNumber = page - 1;
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<TaskEntity> taskPage = taskRepository.findAllByExecutorId(executorId, pageable);
        log.info("Выведен список задач по id исполнителя = {}", executorId);
        return getPageTaskResponse(taskPage);
    }


    public PageTaskResponse getAllTask(int page, int size) {
        int pageNumber = page - 1;
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<TaskEntity> taskPage = taskRepository.findAll(pageable);
        log.info("Выведен список всех задач");
        return getPageTaskResponse(taskPage);
    }

    public String deleteTask(Long id, Long userId) {
        var task = getTaskEntityByTaskId(id);
        if (task.getAuthor().getId().equals(userId)) {
            taskRepository.delete(task);
            log.info("Задача с id: {} удалена", id);
            return String.format("Задача с id: %s удалена", id);
        }
        log.info("Задача не удалена. Пользователь с id: {} не является автором данной задачи", userId);
        throw new TaskUserMismatchException(
                String.format("Задача не удалена. Пользователь с id: %s не является автором данной задачи", userId));
    }


    private PageTaskResponse getPageTaskResponse(Page<TaskEntity> taskPage) {
        var res = taskPage.getContent();
        var totalPage = taskPage.getTotalPages();
        var totalElements = taskPage.getTotalElements();
        var pageNumber = taskPage.getNumber() + 1;
        return new PageTaskResponse(totalPage, totalElements, pageNumber, taskMapper.entityToTaskDtoList(res));
    }

    private TaskEntity createTaskEntity(TaskDto task, UserEntity user) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setTaskStatus(task.getTaskStatus());
        taskEntity.setPriority(task.getPriority());
        taskEntity.setExecutor(getUserEntity(task.getTaskExecutorId()));
        taskEntity.setAuthor(user);
        return taskRepository.save(taskEntity);
    }

    private UserEntity getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new UsernameNotFoundException("Пользователь с данным ID не найден"));
    }

    private TaskEntity getTaskEntityByTaskId(Long id) {
        return taskRepository.findById(id).orElseThrow(()
                -> new TaskNotFoundException("Задача с данным ID не найдена"));
    }
}
