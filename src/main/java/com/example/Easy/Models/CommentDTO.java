package com.example.Easy.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class CommentDTO {
    private UUID commentId;
    private String text;
    @JsonIgnoreProperties({"image","userToken","role"})
    private UserDTO author;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID newsId;
}
