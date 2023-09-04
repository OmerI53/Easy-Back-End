package com.example.Easy.Mappers;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Models.NewsDTO;

public interface NewsMapper {
    NewsDTO toNewsDTO(NewsEntity newsEntity);
    NewsEntity toNewsEntity(NewsDTO newsDTO);
}
