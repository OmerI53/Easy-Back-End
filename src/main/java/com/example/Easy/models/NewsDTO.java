package com.example.Easy.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDTO {

    private UUID newsId;
    private String title;
    private String text;
    private String image;
    private LocalDateTime creationTime;
    private UserDTO author;
    private CategoryDTO category;
    private List<CommentDTO> comments;
    private List<RecordsDTO> newsRecord;
    private int postLikes;
    private int postBookmarks;
    private int postViews;

}
