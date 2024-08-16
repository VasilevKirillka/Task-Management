package com.example.Task_Management.service;

import com.example.Task_Management.dto.CommentDto;
import com.example.Task_Management.entity.CommentEntity;
import com.example.Task_Management.entity.TaskEntity;
import com.example.Task_Management.exceptionHandler.exceptionDto.TaskNotFoundException;
import com.example.Task_Management.mapper.CommentMapper;
import com.example.Task_Management.repository.CommentRepository;
import com.example.Task_Management.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;


    public CommentDto addComment(CommentDto dto){
        var task= getTaskEntityByTaskId(dto.getTaskId());
        CommentEntity commentEntity=new CommentEntity();
        commentEntity.setComment(dto.getComment());
        commentEntity.setTask(task);
        var comment= commentRepository.save(commentEntity);
        taskRepository.save(task);
        log.info("Комментарий к задаче с id {} добавлен", dto.getTaskId());
        return commentMapper.entityToCommentsDto(comment);
    }

    private TaskEntity getTaskEntityByTaskId(Long id) {
        return taskRepository.findById(id).orElseThrow(()
                -> new TaskNotFoundException("Задача с данным ID не найдена"));
    }
}
