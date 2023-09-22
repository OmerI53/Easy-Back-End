package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Builder
@Data
public class CreateNewsRequest {

    private UUID userId;
    private String category;
    private String title;
    private String text;
    private MultipartFile image;
}
