package com.example.Task_Management.mapper;

import com.example.Task_Management.dto.TaskDto;
import com.example.Task_Management.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface TaskMapper {

    @Mapping(source = "author.id", target = "taskAuthorId")
    @Mapping(source = "executor.id", target = "taskExecutorId")
    @Mapping(source = "comments", target = "comments")
    TaskDto entityToTaskDto(TaskEntity entity);

    List<TaskDto> entityToTaskDtoList(List<TaskEntity> entity);

    TaskEntity dtoToTaskEntity(TaskDto dto);

    List<TaskEntity> dtoToTaskEntityList(List<TaskDto> dto);

}
