package com.example.Easy.Services;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Mappers.DeviceMapper;
import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Repository.DeviceRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    @Autowired
    NotificationService notificationService;
    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    public void addNewDevice(DeviceDTO deviceDTO) throws FirebaseMessagingException {
        //TODO cant bootstrap data since a real FCM is needed
        notificationService.subscribeToTopic("All",deviceDTO.getDeviceToken());
        deviceRepository.save(deviceMapper.toDeviceEntity(deviceDTO));
    }
    public void removeDeviceById(UUID deviceId) {
        deviceRepository.deleteById(deviceId);
    }
    public List<DeviceDTO> listAllDevices() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public void patchDevice(UUID deviceId, DeviceDTO deviceDTO) {
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId).orElse(null);
        if(deviceEntity==null)
                return;
        if(deviceDTO.getDeviceToken()!=null & deviceDTO.getDeviceToken()!="")
            deviceEntity.setDeviceToken(deviceDTO.getDeviceToken());
        if(deviceDTO.getTimeZone()!=null & deviceDTO.getDeviceToken()!="")
            deviceEntity.setTimeZone(deviceDTO.getTimeZone());
        if(deviceDTO.getDeviceType()!=null)
            deviceEntity.setDeviceType(deviceDTO.getDeviceType());

        deviceRepository.save(deviceEntity);
    }
}
