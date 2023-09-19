package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class LogoutRequest {
    private final UUID deviceId;
    private final UUID userId;
}
