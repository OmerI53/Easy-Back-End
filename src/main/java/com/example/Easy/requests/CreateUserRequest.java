package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
    private MultipartFile image;
    private Integer role;
}
