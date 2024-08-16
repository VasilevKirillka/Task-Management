package com.example.Task_Management.controller;

import com.example.Task_Management.dto.CommentDto;
import com.example.Task_Management.service.CommentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Добавление комментария", description = "Добавление комментария к задаче")
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;
    @Operation(summary = "Добавление комментария",
            description = "Метод добавления комментария к определенной задаче")
    @PostMapping("/comment")
    public ResponseEntity<CommentDto> addComment(@Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentServiceImpl.addComment(commentDto));
    }
}
