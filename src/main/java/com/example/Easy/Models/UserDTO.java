package com.example.Easy.Models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDTO {
    private UUID userId;
    private String name;
    private String image;
    private String userToken;
}
