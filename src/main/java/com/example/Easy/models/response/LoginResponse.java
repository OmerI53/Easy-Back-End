package com.example.Easy.models.response;


import com.example.Easy.models.AuthenticatedUserDTO;
import com.example.Easy.requests.CreateUserRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
@Getter
@Setter
public class LoginResponse {
    private UUID userId;
    private String name;
    private String image;
    private String email;
    private String jwt;
    public LoginResponse(AuthenticatedUserDTO authResponseDTO) {
        this.userId = authResponseDTO.getUserId();
        this.name = authResponseDTO.getName();
        this.image = authResponseDTO.getName();
        this.email = authResponseDTO.getEmail();
        this.jwt = authResponseDTO.getJwt();
    }


    public LoginResponse(CreateUserRequest createUserRequest, Map<String, String> map) {
        this.email = createUserRequest.getEmail();
        this.name = createUserRequest.getName();
        this.jwt = map.get("jwt");
        this.image = map.get("imageUrl");
        this.userId = UUID.fromString(map.get("userId"));
    }
}
