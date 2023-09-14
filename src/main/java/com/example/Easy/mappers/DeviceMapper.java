package com.example.Easy.mappers;

import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.models.DeviceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceMapper {
    DeviceEntity toDeviceEntity(DeviceDTO deviceDTO);
    DeviceDTO toDeviceDTO(DeviceEntity deviceEntity);
}
