package com.example.Easy.Models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NewsDTO {
    private UUID newsUUID;
    private String title;
    private String text;
    private String image;
}
