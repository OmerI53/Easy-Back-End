package com.example.Easy.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class NewsCategoryDTO {
    private Long categoryId;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private NewsCategoryDTO parent;
}

