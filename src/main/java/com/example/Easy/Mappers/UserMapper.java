package com.example.Easy.Mappers;

import com.example.Easy.Entites.UserEntity;
import com.example.Easy.Models.UserDTO;
import org.hibernate.annotations.SQLInsert;

public interface UserMapper {
    UserEntity toUserEntity(UserDTO userDTO);
    UserDTO toUserDTO(UserEntity userEntity);
}
