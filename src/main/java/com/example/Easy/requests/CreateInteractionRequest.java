package com.example.Easy.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateInteractionRequest {
    private UUID userId;
    private UUID newsId;
    private Boolean like;
    private Boolean bookmark;
}
