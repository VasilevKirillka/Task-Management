package com.example.Task_Management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Токен")
public class AuthResponse {
    @Schema(description = "Токен для аутентификации", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxd2VydHkiLCJpYXQiOjE3MjMxMDEzODAsImV4cCI6MTcyMzEwMTk4MH0.g92BvhMHV7RuGP6XHYYsRsvJDVnuSllNbQyqEMas_LY6u49-w250tN3zTx5cl-U3oJHDyXhwcwRZCPypA6FbcQ")
    private String token;
}
