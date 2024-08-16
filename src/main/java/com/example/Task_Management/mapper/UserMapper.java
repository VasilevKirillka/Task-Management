package com.example.Task_Management.mapper;

import com.example.Task_Management.dto.UserDto;
import com.example.Task_Management.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    UserDto userToUserDto(UserEntity user);

    List<UserDto> usersToUserDtos(List<UserEntity> users);
}
