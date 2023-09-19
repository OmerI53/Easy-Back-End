package com.example.Easy.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RecordsDTO {

    private UUID recordId;
    private UserDTO user;
    private CategoryDTO newsCategory;
    private NewsDTO news;
    private int repeatedRead;
    private boolean postlike;
    private boolean postbookmark;


}
