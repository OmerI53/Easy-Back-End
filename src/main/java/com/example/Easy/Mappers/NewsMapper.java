package com.example.Easy.Mappers;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Models.NewsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NewsMapper {
    NewsDTO toNewsDTO(NewsEntity newsEntity);
    NewsEntity toNewsEntity(NewsDTO newsDTO);
}
