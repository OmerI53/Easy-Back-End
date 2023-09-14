package com.example.Easy.mappers;

import com.example.Easy.entities.NewsCategoryEntity;
import com.example.Easy.models.NewsCategoryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NewsCategoryMapper {
    NewsCategoryEntity toNewsCategoryEntity(NewsCategoryDTO newsCategoryDTO);
    NewsCategoryDTO toNewsCategoryDTO(NewsCategoryEntity newsEntity);
}
