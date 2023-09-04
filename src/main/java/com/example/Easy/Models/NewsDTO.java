package com.example.Easy.Models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Builder
public class NewsDTO {

    private UUID newsId;
    private String title;
    private String text;
    private String image;
    private UserDTO author;
    private LocalDateTime creationTime;
    private NewsCategoryDTO category;
    private List<CommentDTO> comments;

}
