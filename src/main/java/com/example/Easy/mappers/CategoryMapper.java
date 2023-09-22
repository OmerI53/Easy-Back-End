package com.example.Easy.mappers;

import com.example.Easy.entities.CategoryEntity;
import com.example.Easy.models.CategoryDTO;

public interface CategoryMapper {
    CategoryEntity toCategoryEntity(CategoryDTO newsCategoryDTO);
    CategoryDTO toCategoryDTO(CategoryEntity newsEntity);
}
