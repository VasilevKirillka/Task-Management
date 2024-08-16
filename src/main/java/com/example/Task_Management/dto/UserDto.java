package com.example.Task_Management.dto;

import com.example.Task_Management.entity.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные пользователя")
public class UserDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Имя пользователя/логин", example = "user")
    private String username;
    @Schema(description = "Почта", example = "qwerty")
    private String email;
    @Schema(description = "Роль пользователя", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
    private List<RoleType> roles=new ArrayList<>();
}
