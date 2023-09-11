package com.example.Easy.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonIgnoreProperties({"image","userToken","role","email","password"})
    private UserDTO author;
    private LocalDateTime creationTime;
    private NewsCategoryDTO category;
    private List<CommentDTO> comments;

}
