package com.example.Easy.mappers;

import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    public static UserEntity toUserEntity(UserDTO userDTO){
        return new UserEntity(
                userDTO.getUserId(),
                userDTO.getName(),
                userDTO.getImage(),
                userDTO.getPassword(),
                userDTO.getRole(),
                userDTO.getEmail()
        );
    }
    public static UserDTO toUserDTO(UserEntity userEntity){
        return new UserDTO(
                userEntity.getUserId(),
                userEntity.getName(),
                userEntity.getImage(),
                userEntity.getRole(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getUserRecords(),
                userEntity.getFollowing(),
                userEntity.getComments(),
                userEntity.getDevice(),
                userEntity.getFollowers(),
                userEntity.getNews(),
                userEntity.getUsername(),
                userEntity.getAuthorities()
        );
    }
}

