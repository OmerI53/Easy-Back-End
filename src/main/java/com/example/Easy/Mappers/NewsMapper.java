package com.example.Easy.Mappers;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Repository.NewsRepository;
import org.mapstruct.Mapper;

@Mapper
public interface NewsMapper {

    NewsEntity toNewsEntity(NewsDTO newsDTO);

    NewsDTO newsDTO(NewsEntity newsEntity);

}
