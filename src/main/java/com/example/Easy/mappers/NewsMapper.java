package com.example.Easy.mappers;

import com.example.Easy.entities.NewsEntity;
import com.example.Easy.models.NewsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NewsMapper {
    NewsDTO toNewsDTO(NewsEntity newsEntity);
    NewsEntity toNewsEntity(NewsDTO newsDTO);
}

