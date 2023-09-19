package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateReadRequest {
    private UUID userId;
    private UUID newsId;
}
