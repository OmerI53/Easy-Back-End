package com.example.Easy.models.response;

import com.example.Easy.models.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentResponse extends BaseResponse{
    private String text;
    private UUID userId;
    private UUID newsId;
    public CommentResponse(CommentDTO commentDTO) {
        this.text = commentDTO.getText();
        this.userId=commentDTO.getAuthor().getUserId();
        this.newsId=commentDTO.getNews().getNewsId();
    }
}
