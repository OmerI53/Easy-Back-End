package com.example.Easy.Mappers;

import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Models.UserDTO;

public interface UserMapper {
    UserEntity toUserEntity(UserDTO userDTO);
    UserDTO toUserDTO(UserEntity userEntity);
}
