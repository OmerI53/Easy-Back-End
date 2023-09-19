package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateCommentRequest {
    private String text;
    private UUID userId;
    private UUID newsId;
}
