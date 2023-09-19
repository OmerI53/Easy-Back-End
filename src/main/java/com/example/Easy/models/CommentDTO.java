package com.example.Easy.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CommentDTO {
    private UUID commentId;
    private UserDTO author;
    private NewsDTO news;
    private String text;
    private LocalDateTime creationTime;
}
