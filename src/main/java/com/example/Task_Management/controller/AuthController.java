package com.example.Task_Management.controller;

import com.example.Task_Management.dto.AuthRequest;
import com.example.Task_Management.dto.AuthResponse;
import com.example.Task_Management.dto.RegistrationUserRequest;
import com.example.Task_Management.service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Аутентификация и регистрация", description = "Методы генерации токена и регистрации пользователя")
public class AuthController {
    private final AuthServiceImpl authServiceImpl;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Создание пользователя",
            description = "Метод создания нового пользователя на основании регистрационный данных")
    @PostMapping("/registration")
    public ResponseEntity<String> createUser(@Valid @RequestBody RegistrationUserRequest regUserDto) {
        authServiceImpl.createUser(regUserDto);
        return ResponseEntity.ok().body("Пользователь создан");
    }

    @Operation(summary = "Создание токена",
            description = "Метод создания токена аутентификации на основании логина и пароля")
    @GetMapping("/token")
    public ResponseEntity<AuthResponse> getToken(@Valid @RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                    authRequest.getPassword()));
            return ResponseEntity.ok(authServiceImpl.generateToken(authRequest.getEmail()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неправильный логин или пароль");
        }
    }
}
