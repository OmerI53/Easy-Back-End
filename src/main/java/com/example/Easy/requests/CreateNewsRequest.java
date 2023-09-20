package com.example.Easy.requests;

import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Builder
@Data
public class CreateNewsRequest {
    @NotNull
    @NotBlank
    private UUID userId;
    @NotNull
    @NotBlank
    private String category;
    private String title;
    private String text;
    private MultipartFile image;
}
