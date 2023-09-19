package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoginRequest {
    private UUID deviceId;
    private String email;
    private String password;
}
