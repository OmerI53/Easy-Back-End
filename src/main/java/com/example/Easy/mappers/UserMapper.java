package com.example.Easy.mappers;


import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.UserDTO;

public interface UserMapper {
    UserDTO toUserDTO(UserEntity userEntity);
    UserEntity toUserEntity(UserDTO userDTO);
}
