package com.example.Task_Management.mapper;

import com.example.Task_Management.dto.CommentDto;
import com.example.Task_Management.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CommentMapper {

    @Mapping(source = "task.id", target = "taskId")
    CommentDto entityToCommentsDto(CommentEntity entity);

    List<CommentDto> entityToCommentDtoList (List <CommentEntity> entityList);


}
