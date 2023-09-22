package com.example.Easy.mappers;

import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.models.DeviceDTO;


public interface DeviceMapper {
    DeviceEntity toDeviceEntity(DeviceDTO deviceDTO);
    DeviceDTO toDeviceDTO(DeviceEntity deviceEntity);

}
