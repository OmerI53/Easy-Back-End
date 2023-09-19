package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCategoryRequest {
    private String name;
    private String parent;
}
