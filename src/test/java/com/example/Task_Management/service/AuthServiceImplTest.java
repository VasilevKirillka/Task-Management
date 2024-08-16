package com.example.Task_Management.service;

import com.example.Task_Management.dto.AuthResponse;
import com.example.Task_Management.dto.RegistrationUserRequest;
import com.example.Task_Management.entity.UserEntity;
import com.example.Task_Management.repository.UserRepository;
import com.example.Task_Management.security.jwt.JwtTokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @InjectMocks
    private AuthServiceImpl authService;


    private final static String EMAIL = "qwerty";
    private final static String PASSWORD = "123";
    private final static String TOKEN = "token";

    @DisplayName("Регистрация нового пользователя")
    @Test
    void testCreateUser() {
        RegistrationUserRequest request = new RegistrationUserRequest(null, EMAIL, PASSWORD);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        authService.createUser(request);

        verify(userRepository).save(any(UserEntity.class));
        verify(userRepository).findByEmail(request.getEmail());

    }

    @DisplayName("Регистрация нового пользователя если email уже занят другим пользователем")
    @Test
    public void testCreateUser_UserAlreadyExists() {
        RegistrationUserRequest request = new RegistrationUserRequest(null, EMAIL, PASSWORD);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new UserEntity()));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authService.createUser(request);
        });

        assertEquals("Пользователь с таким email уже существует", thrown.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @DisplayName("Создание токена")
    @Test
    void testGenerateToken() {
        when(jwtTokenUtils.generateToken(EMAIL)).thenReturn(TOKEN);

        AuthResponse response = authService.generateToken(EMAIL);

        assertNotNull(response);
        assertEquals(TOKEN, response.getToken());
        verify(jwtTokenUtils, times(1)).generateToken(EMAIL);
    }
}