package com.example.Easy.mappers;

import com.example.Easy.entities.CategoryEntity;
import com.example.Easy.models.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public CategoryDTO toCategoryDTO(CategoryEntity newsEntity) {
        if (newsEntity == null) {
            return null;
        }

        return CategoryDTO.builder()
                .categoryId(newsEntity.getCategoryId())
                .name(newsEntity.getName())
                .parent(toCategoryDTO(newsEntity.getParent()))
                .build();
    }

    @Override
    public CategoryEntity toCategoryEntity(CategoryDTO newsCategoryDTO) {
        if (newsCategoryDTO == null) {
            return null;
        }
        return CategoryEntity.builder()
                .categoryId(newsCategoryDTO.getCategoryId())
                .name(newsCategoryDTO.getName())
                .parent(toCategoryEntity(newsCategoryDTO.getParent())).build();
    }


}
