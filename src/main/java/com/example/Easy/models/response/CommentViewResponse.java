package com.example.Easy.models.response;

import com.example.Easy.models.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CommentViewResponse {
    private String name;
    private UUID userId;
    private String image;
    private String text;
    private LocalDateTime creationTime;

    public CommentViewResponse(CommentDTO commentDTO) {
        this.name = commentDTO.getAuthor().getName();
        this.userId = commentDTO.getAuthor().getUserId();
        this.image=commentDTO.getAuthor().getImage();
        this.text = commentDTO.getText();
        this.creationTime=commentDTO.getCreationTime();
    }
}
