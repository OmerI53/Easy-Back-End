package com.example.Easy.Services;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Mappers.DeviceMapper;
import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public void deleteDevice(DeviceDTO deviceDTO){
        deviceRepository.delete(deviceMapper.toDeviceEntity(deviceDTO));

    }
    public void addNewDevice(DeviceDTO deviceDTO) {
        deviceRepository.save(deviceMapper.toDeviceEntity(deviceDTO));
    }
}
