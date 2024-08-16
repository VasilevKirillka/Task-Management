package com.example.Task_Management.service;

import com.example.Task_Management.dto.PageTaskResponse;
import com.example.Task_Management.dto.TaskDto;
import com.example.Task_Management.entity.*;
import com.example.Task_Management.mapper.TaskMapper;
import com.example.Task_Management.repository.TaskRepository;
import com.example.Task_Management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;


    private final static String EMAIL = "qwerty";
    private final static String PASSWORD = "123";

    private UserEntity author;
    private UserEntity executor;
    private TaskDto taskDto;

    @BeforeEach
    public void setUp() {


        author=new UserEntity(1L, null, EMAIL, PASSWORD, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>());

        executor=new UserEntity(2L, null, EMAIL, PASSWORD, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>());

        taskDto =new TaskDto(1L, "Title", "Description", TaskStatus.PENDING,
                TaskPriority.HIGH, author.getId(), executor.getId(), null);
    }


    @DisplayName("Создание новой задачи")
    @Test
    public void testCreateTask() {

        TaskEntity taskEntity = new TaskEntity();
        when(taskMapper.entityToTaskDto(any(TaskEntity.class))).thenReturn(taskDto);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);
        when(userRepository.findById(any())).thenReturn(Optional.of(executor));

        TaskDto result = taskService.createTask(taskDto, author);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        assertEquals(taskDto.getDescription(), result.getDescription());
        assertEquals(taskDto.getTaskStatus(), result.getTaskStatus());
        assertEquals(taskDto.getPriority(), result.getPriority());

        verify(taskMapper).entityToTaskDto(any(TaskEntity.class));
        verify(taskRepository).save(any(TaskEntity.class));

    }

    @DisplayName("Вывод задачи по ее id")
    @Test
    void getTaskById() {
        TaskEntity taskEntity = new TaskEntity();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.entityToTaskDto(any(TaskEntity.class))).thenReturn(taskDto);

        TaskDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(taskDto.getId(), result.getId());
        assertEquals(taskDto.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).findById(any());

    }


    @DisplayName("Вывод списка задачь по id автора")
    @Test
    void getTasksByAuthorId() {
        int page = 1;
        int size = 2;

        TaskEntity taskEntity1 = new TaskEntity();
        taskEntity1.setAuthor(author);
        TaskEntity taskEntity2 = new TaskEntity();
        taskEntity2.setAuthor(author);
        TaskEntity taskEntity3 = new TaskEntity();
        taskEntity3.setAuthor(author);

        List<TaskEntity> taskList = new ArrayList<>();

        taskList.add(taskEntity1);
        taskList.add(taskEntity2);
        taskList.add(taskEntity3);

        Page<TaskEntity> taskPage = new PageImpl<>(taskList, PageRequest.of(0, size), taskList.size());
        when(userRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(taskRepository.findAllByAuthorId(1L, PageRequest.of(0, size))).thenReturn(taskPage);

        PageTaskResponse result = taskService.getTasksByAuthorId(author.getId(), page, size);

        assertNotNull(result);
        assertEquals(1, result.getPageNumber());
        assertEquals(2, result.getTotalPage());
        assertEquals(3, result.getTotalElement());

        verify(taskRepository).findAllByAuthorId(1L, PageRequest.of(0, size));

    }


    @DisplayName("Вывод всех задач")
    @Test
    void getAllTask() {
        int page = 1;
        int size = 2;

        List<TaskEntity> taskList = new ArrayList<>();

        taskList.add(new TaskEntity());
        taskList.add(new TaskEntity());
        taskList.add(new TaskEntity());
        Page<TaskEntity> taskPage = new PageImpl<>(taskList, PageRequest.of(0, size), taskList.size());

        when(taskRepository.findAll(PageRequest.of(0, size))).thenReturn(taskPage);

        PageTaskResponse result = taskService.getAllTask(page, size);

        assertNotNull(result);
        assertEquals(1, result.getPageNumber());
        assertEquals(2, result.getTotalPage());
        assertEquals(3, result.getTotalElement());

        verify(taskRepository).findAll(PageRequest.of(0, size));
    }

    @DisplayName("Удачение автором задачи по ее id")
    @Test
    void deleteTask() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setAuthor(author);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));
        String result = taskService.deleteTask(1L, author.getId());
        assertNotNull(result);
        assertEquals(String.format("Задача с id: %s удалена", 1L), result);
        verify(taskRepository, times(1)).delete(any());
    }

    @DisplayName("Удачение задачи по ее id, если пользователь не является ее автором")
    @Test
    void deleteTask_NotValid_AuthorId() {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setAuthor(author);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
            taskService.deleteTask(1L, 2L)
        );

        assertEquals(String.format("Задача не удалена. Пользователь с id: %s не является автором данной задачи", 2L),
                thrown.getMessage());
        verify(taskRepository, never()).delete(any(TaskEntity.class));
    }
}