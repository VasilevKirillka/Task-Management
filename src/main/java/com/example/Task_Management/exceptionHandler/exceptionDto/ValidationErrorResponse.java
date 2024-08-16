package com.example.Task_Management.exceptionHandler.exceptionDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с ошибкой валидации")
public class ValidationErrorResponse {
    @Schema(description = "Поле с ошибкой", example = "email")
    private String fieldName;
    @Schema(description = "Сообщение об ошибке", example = "Поле email не должно быть пустым")
    private String message;

}
