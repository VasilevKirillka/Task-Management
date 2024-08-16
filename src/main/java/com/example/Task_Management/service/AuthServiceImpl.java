package com.example.Task_Management.service;


import com.example.Task_Management.dto.AuthResponse;
import com.example.Task_Management.dto.RegistrationUserRequest;
import com.example.Task_Management.exceptionHandler.exceptionDto.UnauthorizedExceptionResponse;
import com.example.Task_Management.entity.RoleType;
import com.example.Task_Management.entity.UserEntity;
import com.example.Task_Management.exceptionHandler.exceptionDto.UserRegistrationException;
import com.example.Task_Management.repository.UserRepository;
import com.example.Task_Management.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final JwtTokenUtils jwtTokenUtils;

    public void createUser(RegistrationUserRequest registrationUserRequest) {
        isExistingUser(registrationUserRequest);
        UserEntity user = new UserEntity();
        user.setUsername(registrationUserRequest.getUsername());
        user.setEmail(registrationUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserRequest.getPassword()));
        user.setRoles(List.of(RoleType.ROLE_USER));
        userRepository.save(user);
        log.info("Пользователь {} зарегистрирован", user.getEmail());
    }


    public AuthResponse generateToken(String email){
        String token = jwtTokenUtils.generateToken(email);
        log.info("Токен: {}", token);
        return new AuthResponse(token);
    }

    public UserEntity getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("Не удалось получить текущего пользователя. Пользователь не аутентифицирован.");
            throw new UnauthorizedExceptionResponse("Пользователь не аутентифицирован");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()-> new UnauthorizedExceptionResponse("Пользователь не найден"));
    }


    private void isExistingUser(RegistrationUserRequest registrationUserRequest) {
        if (userRepository.findByEmail(registrationUserRequest.getEmail()).isPresent()){
            throw new UserRegistrationException("Пользователь с таким email уже существует");
        }
    }

}
