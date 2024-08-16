package com.example.Task_Management.exceptionHandler.exceptionDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ ошибкой")
public class ErrorResponse {
    @Schema(description = "Сообщение ошибки", example = "Задачи с данным ID не найдено")
    private String message;
}
