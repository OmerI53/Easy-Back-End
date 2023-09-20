package com.example.Easy.mappers;

import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.models.DeviceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceMapper {

    public static DeviceDTO toDeviceDTO(DeviceEntity deviceEntity){
        return new DeviceDTO(
                deviceEntity.getDeviceID(),
                deviceEntity.getTimeZone(),
                deviceEntity.getDeviceType(),
                deviceEntity.getDeviceToken(),
                deviceEntity.getUserId(),
                deviceEntity.getUsers()
        );
    }
    public static DeviceEntity toDeviceEntity(DeviceDTO deviceDTO){
        return new DeviceEntity(
                deviceDTO.getDeviceID(),
                deviceDTO.getTimeZone(),
                deviceDTO.getDeviceType(),
                deviceDTO.getDeviceToken()
        );
    }
}

