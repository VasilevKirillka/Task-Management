package com.example.Task_Management.controller;

import com.example.Task_Management.dto.PageTaskResponse;
import com.example.Task_Management.dto.TaskDto;
import com.example.Task_Management.entity.UserEntity;
import com.example.Task_Management.exceptionHandler.exceptionDto.PageValidateException;
import com.example.Task_Management.service.AuthServiceImpl;
import com.example.Task_Management.service.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Контроллер для управления задачами", description = "Позволяет создавать новые задачи, редактировать" +
        "  и удалять существующие, получать задачи по id или страницей со списком, менять статус задачи, " +
        "назначать исполнителя")
public class TaskController {
    private final TaskServiceImpl taskServiceImpl;
    private final AuthServiceImpl authServiceImpl;


    @Operation(summary = "Создание новой задачи",
            description = "Метод создания новой задачи. Доступно всем аутентифицированным пользователям")
    @PostMapping("/task")
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto task) {
        UserEntity currentUser = authServiceImpl.getCurrentUserId();
        return ResponseEntity.ok(taskServiceImpl.createTask(task, currentUser));
    }

    @Operation(summary = "Изменение задачи",
            description = "Метод для изменения задачи. Доступен для автора и исполнителя задачи")
    @PostMapping("/updatetask/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto task) {
        UserEntity currentUser = authServiceImpl.getCurrentUserId();
        return ResponseEntity.ok(taskServiceImpl.updateTask(id, task, currentUser.getId()));
    }

    @Operation(summary = "Получение задачи",
            description = "Метод для получения задачи по ее id. Доступно всем аутентифицированным пользователям")
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskServiceImpl.getTaskById(id));
    }

    @Operation(summary = "Получение списка задач текущего пользователя",
            description = "Метод для получения страницы со списком задач для текущего пользователя." +
                    " Доступно всем аутентифицированным пользователям")
    @GetMapping("/getAllCurrentUserTask")
    public ResponseEntity<PageTaskResponse> getAllUserTasks(@RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "5") int size) {
        validateParam(page, size);
        Long currentUser = authServiceImpl.getCurrentUserId().getId();
        return ResponseEntity.ok(taskServiceImpl.getAllUserTask(currentUser, page, size));
    }


    @Operation(summary = "Получение списка задач по определенному автору",
            description = "Метод для получения страницы со списком задач по id автора." +
                    " Доступно всем аутентифицированным пользователям")
    @GetMapping("/getAllAuthorTask/{authorId}")
    public ResponseEntity<PageTaskResponse> getAllTaskByAuthorId(@PathVariable Long authorId,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        validateParam(page, size);
        return ResponseEntity.ok(taskServiceImpl.getTasksByAuthorId(authorId, page, size));
    }

    @Operation(summary = "Получение списка задач по определенному исполнителю",
            description = "Метод для получения страницы со списком задач по id исполнителя." +
                    " Доступно всем аутентифицированным пользователям")
    @GetMapping("/getAllExecutorTask/{executorId}")
    public ResponseEntity<PageTaskResponse> getAllTaskByExecutorId(@PathVariable Long executorId,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "5") int size) {
        validateParam(page, size);
        return ResponseEntity.ok(taskServiceImpl.getAllTaskByExecutorId(executorId, page, size));
    }

    @Operation(summary = "Получение списка всех задач",
            description = "Метод для получения страницы со списком всех задач." +
                    " Доступно всем аутентифицированным пользователям")
    @GetMapping("/getAll")
    public ResponseEntity<PageTaskResponse> getAllTask(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "5") int size) {
        validateParam(page, size);
        return ResponseEntity.ok(taskServiceImpl.getAllTask(page, size));
    }

    @Operation(summary = "Удаления задачи",
            description = "Метод для удаления задачи" +
                    " Задачу может удалить только ее автор")
    @GetMapping("delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        UserEntity currentUser = authServiceImpl.getCurrentUserId();
        return ResponseEntity.ok(taskServiceImpl.deleteTask(id, currentUser.getId()));

    }

    private void validateParam(int page, int size) {
        if (page < 1 || size < 1) {
            throw new PageValidateException("Значение страницы или размера списка не должны быть меньше 1");
        }
    }
}
