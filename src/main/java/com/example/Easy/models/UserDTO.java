package com.example.Easy.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class UserDTO {
    private UUID userId;
    private String name;
    private String image;
    private Integer role;
    private String email;
    private String password;
}


