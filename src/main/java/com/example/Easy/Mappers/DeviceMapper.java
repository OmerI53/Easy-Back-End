package com.example.Easy.Mappers;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Models.DeviceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceMapper {
    DeviceEntity toDeviceEntity(DeviceDTO deviceDTO);
    DeviceDTO toDeviceDTO(DeviceEntity deviceEntity);
}
