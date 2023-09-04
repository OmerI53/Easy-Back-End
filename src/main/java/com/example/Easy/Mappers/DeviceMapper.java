package com.example.Easy.Mappers;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Models.DeviceDTO;

public interface DeviceMapper {
    DeviceEntity toDeviceEntity(DeviceDTO deviceDTO);
    DeviceDTO toDeviceDTO(DeviceEntity deviceEntity);
}
