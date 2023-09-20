package com.example.Easy.models;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.entities.NewsCategoryEntity;
import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public NewsDTO(UUID newsId, String title, String text, String image, UserEntity author, LocalDateTime creationTime, NewsCategoryEntity category, List<CommentEntity> comments, List<RecordsEntity> newsRecord) {
    }
}

