package com.example.Easy.mappers;

import com.example.Easy.entities.CategoryEntity;
import com.example.Easy.models.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NewsCategoryMapper {
    CategoryEntity toNewsCategoryEntity(CategoryDTO newsCategoryDTO);
    CategoryDTO toNewsCategoryDTO(CategoryEntity newsEntity);
}
