package com.example.Task_Management.service;

import com.example.Task_Management.dto.AuthResponse;
import com.example.Task_Management.dto.RegistrationUserRequest;

public interface AuthService {
    void createUser(RegistrationUserRequest registrationUserRequest);
    AuthResponse generateToken(String email);
}
