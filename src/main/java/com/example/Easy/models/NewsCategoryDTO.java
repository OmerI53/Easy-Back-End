package com.example.Easy.models;

import com.example.Easy.entities.NewsCategoryEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.RecordsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsCategoryDTO {
    private Long categoryId;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private NewsCategoryDTO parent;

    public NewsCategoryDTO(Long categoryId, String name, NewsCategoryEntity parent, List<NewsEntity> news, List<RecordsEntity> categoryRecord, Set<NewsCategoryEntity> children) {
    }
}

