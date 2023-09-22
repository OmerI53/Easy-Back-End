package com.example.Easy.mappers;

import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.UserDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class DeviceMapperImpl implements DeviceMapper {
    @Override
    public DeviceDTO toDeviceDTO(DeviceEntity deviceEntity) {
        if (deviceEntity == null)
            return null;
        return DeviceDTO.builder()
                .timeZone(deviceEntity.getTimeZone())
                .deviceType(deviceEntity.getDeviceType())
                .deviceID(deviceEntity.getDeviceID())
                .deviceToken(deviceEntity.getDeviceToken())
                .users(usersListMapper(deviceEntity.getUsers()))
                .build();
    }

    private List<UserDTO> usersListMapper(List<UserEntity> users) {
        if (users == null)
            return null;
        List<UserDTO> userDTOS = new LinkedList<>();
        for (UserEntity userEntity : users)
            userDTOS.add(toUserDTO(userEntity));
        return userDTOS;
    }

    private UserDTO toUserDTO(UserEntity userEntity) {
        if (userEntity == null)
            return null;
        return UserDTO.builder()
                .email(userEntity.getEmail())
                .userId(userEntity.getUserId())
                .image(userEntity.getImage())
                .name(userEntity.getName())
                .role(userEntity.getRole())
                .build();
    }

    @Override
    public DeviceEntity toDeviceEntity(DeviceDTO deviceDTO) {
        if (deviceDTO == null)
            return null;
        return DeviceEntity.builder()
                .timeZone(deviceDTO.getTimeZone())
                .deviceType(deviceDTO.getDeviceType())
                .deviceID(deviceDTO.getDeviceID())
                .deviceToken(deviceDTO.getDeviceToken())
                .users(usersListMapperEntity(deviceDTO.getUsers()))
                .build();
    }

    private List<UserEntity> usersListMapperEntity(List<UserDTO> users) {
        if (users == null)
            return null;
        List<UserEntity> userEntities = new LinkedList<>();
        for (UserDTO userDTO : users)
            userEntities.add(toUserEntity(userDTO));
        return userEntities;
    }

    private UserEntity toUserEntity(UserDTO userDTO) {
        if (userDTO == null)
            return null;
        return UserEntity.builder()
                .userId(userDTO.getUserId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .role(userDTO.getRole())
                .image(userDTO.getImage())
                .build();
    }

}
