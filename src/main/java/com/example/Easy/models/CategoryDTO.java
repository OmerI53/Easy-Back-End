package com.example.Easy.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryDTO {
    private Long categoryId;
    private String name;
    private CategoryDTO parent;
}
