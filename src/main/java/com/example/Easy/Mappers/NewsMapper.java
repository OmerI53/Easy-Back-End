package com.example.Easy.Mappers;

import com.example.Easy.Entites.NewsEntity;
import com.example.Easy.Models.NewsDTO;

import java.util.Optional;

public interface NewsMapper {
    NewsDTO toNewsDTO(NewsEntity newsEntity);
    NewsEntity toNewsEntity(NewsDTO newsDTO);
}
