package com.example.Easy.models.response;

import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponse extends BaseResponse{
    private UUID newsId;
    private String title;
    private String text;
    private String image;
    private LocalDateTime creationTime;
    private UserDTO author;
    private String category;
    private Integer likes;
    private Integer bookmarks;
    private Integer views;
    public NewsResponse(NewsDTO newsDTO) {
        this.newsId = newsDTO.getNewsId();
        this.title = newsDTO.getTitle();
        this.text = newsDTO.getText();
        this.image = newsDTO.getImage();
        this.creationTime = newsDTO.getCreationTime();
        this.category = newsDTO.getCategory().getName();
        this.likes= newsDTO.getPostLikes();
        this.bookmarks=newsDTO.getPostBookmarks();
        this.views=newsDTO.getPostViews();
        this.author = UserDTO.builder()
                .name(newsDTO.getAuthor().getName())
                .image(newsDTO.getAuthor().getImage())
                .userId(newsDTO.getAuthor().getUserId())
                .build();
    }
}
