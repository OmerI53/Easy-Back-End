package com.example.Easy.models;

import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private UUID commentId;
    private String text;
    @JsonIgnoreProperties({"image","userToken","role"})
    private UserDTO author;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID newsId;


    public CommentDTO(UUID commentId, String text, UserEntity author, NewsEntity news) {
    }
}

