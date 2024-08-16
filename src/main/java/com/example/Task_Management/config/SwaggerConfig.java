package com.example.Task_Management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Task Management System", version = "1.0",
        description = "Task Management System API")
)
public class SwaggerConfig {
}
