package com.example.Easy.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder
public class AuthenticatedUserDTO {
    private UUID userId;
    private String name;
    private String image;
    private String email;
    private String jwt;
}
