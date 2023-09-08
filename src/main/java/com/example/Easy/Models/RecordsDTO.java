package com.example.Easy.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
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

}
