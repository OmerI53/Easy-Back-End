package com.example.Easy.Mappers;

import com.example.Easy.Entities.NewsCategoryEntity;
import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Models.NewsCategoryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NewsCategoryMapper {
    NewsCategoryEntity toNewsCategoryEntity(NewsCategoryDTO newsCategoryDTO);
    NewsCategoryDTO toNewsCategoryDTO(NewsCategoryEntity newsEntity);
}
