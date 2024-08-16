package com.example.Task_Management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;





@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные для регистрации")
public class RegistrationUserRequest {
    @Schema(description = "Имя пользователя/логин", example = "user", nullable = true)
    private String username;
    @Schema(description = "Почта", example = "qwerty")
    @NotNull(message = "Поле email не должно быть пустым")
    private String email;
    @Schema(description = "Пароль", example = "1234", minLength = 3)
    @NotNull(message = "Поле password не должно быть пустым")
    @Size(min = 3, message = "Пароль должен содержать хотя бы 3 символа")
    private String password;
}
