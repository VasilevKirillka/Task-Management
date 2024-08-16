package com.example.Task_Management.dto;

import com.example.Task_Management.entity.CommentEntity;
import com.example.Task_Management.entity.TaskPriority;
import com.example.Task_Management.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные пользователя")
public class TaskDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Название задачи", example = "Купить самолет")
    @NotNull(message = "Поле title не должно быть пустым")
    private String title;
    @Schema(description = "Описание задачи", example = "Поехать на авиасалон и купить там Боинг")
    @NotNull(message = "Поле description не должно быть пустым")
    private String description;
    @Schema(description = "Статус задачи", example = "[\"PENDING\", \"INPROCESS\", \"COMPLETED\"]")
    @NotNull(message = "Поле taskStatus не должно быть пустым")
    private TaskStatus taskStatus;
    @Schema(description = "Приоритет задачи", example = "[\"HIGH\", \"MEDIUM\", \"LOW\"]")
    @NotNull(message = "Поле priority не должно быть пустым")
    private TaskPriority priority;
    @Schema(description = "Автор задачи", example = "1")
    private Long taskAuthorId;
    @Schema(description = "Исполнитель задачи", example = "2")
    private Long taskExecutorId;
    @Schema(description = "Комментарии к задаче")
    private List<CommentEntity> comments = new ArrayList<>();
}
