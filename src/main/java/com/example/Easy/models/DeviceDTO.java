package com.example.Easy.models;

import com.example.Easy.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private UUID deviceID;
    private String timeZone;
    private DeviceType deviceType;
    private String deviceToken;

    public DeviceDTO(UUID deviceID, String timeZone, DeviceType deviceType, String deviceToken, UUID userId, List<UserEntity> users) {
    }
}

