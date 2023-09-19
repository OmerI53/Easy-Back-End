package com.example.Easy.requests;

import com.example.Easy.models.DeviceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDeviceRequest {
    private String timeZone;
    private DeviceType deviceType;
    private String deviceToken;
}
