package com.example.Easy.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeviceDTO {
    private UUID deviceID;
    private String timeZone;
    private DeviceType deviceType;
    private String deviceToken;
}
