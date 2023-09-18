package com.example.Easy.mappers;

import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserEntity toUserEntity(UserDTO userDTO);
    UserDTO toUserDTO(UserEntity userEntity);
}

