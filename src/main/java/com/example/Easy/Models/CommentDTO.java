package com.example.Easy.Models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CommentDTO {

    private UUID commentID;
    private String text;
    private UserDTO author;
    private NewsDTO news;
}
