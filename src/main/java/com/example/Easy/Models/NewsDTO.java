package com.example.Easy.Models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class NewsDTO {

    private Long newsId;
    private String categories;
    private String title;
    private String text;
    private String image;
    private UUID AuthorId;

}
