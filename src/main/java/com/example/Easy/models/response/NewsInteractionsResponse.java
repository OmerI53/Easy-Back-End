package com.example.Easy.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class NewsInteractionsResponse {
    private UUID newsId;
    private Integer likes;
    private Integer bookmarks;
    private Integer views;

    public NewsInteractionsResponse(UUID newsId,Map<String,Integer> dataMap) {
        this.newsId = newsId;
        this.likes = dataMap.get("likes");
        this.bookmarks = dataMap.get("bookmarks");
        this.views = dataMap.get("views");
    }
}
