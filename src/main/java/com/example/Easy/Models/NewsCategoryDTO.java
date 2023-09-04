package com.example.Easy.Models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsCategoryDTO {
    private Long categoryID;
    private String name;
}
