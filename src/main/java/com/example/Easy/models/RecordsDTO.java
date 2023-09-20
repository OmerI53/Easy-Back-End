package com.example.Easy.models;

import com.example.Easy.entities.NewsCategoryEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordsDTO {
    private UUID recordId;

    @JsonIgnoreProperties({"image","userToken","role"})
    private UserDTO user;
    @JsonIgnore
    private NewsCategoryDTO newsCategory;
    @JsonIgnoreProperties({"text","author","creationTime","image"})
    private NewsDTO news;
    private int repeatedRead;
    @JsonIgnore
    private boolean postlike;
    @JsonIgnore
    private boolean postbookmark;

    public RecordsDTO(UUID recordID, UserEntity user, NewsCategoryEntity newsCategory, NewsEntity news, int repeatedRead) {

    }
}

