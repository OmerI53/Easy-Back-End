package com.example.Easy.mappers;

import com.example.Easy.entities.NewsEntity;
import com.example.Easy.models.NewsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NewsMapper {
    public static NewsDTO toNewsDTO(NewsEntity newsEntity){
        return new NewsDTO(
                newsEntity.getNewsId(),
                newsEntity.getTitle(),
                newsEntity.getText(),
                newsEntity.getImage(),
                newsEntity.getAuthor(),
                newsEntity.getCreationTime(),
                newsEntity.getCategory(),
                newsEntity.getComments(),
                newsEntity.getNewsRecord()
        );
    }
    public static NewsEntity toNewsEntity(NewsDTO newsDTO){
        return new NewsEntity(
                newsDTO.getNewsId(),
                newsDTO.getTitle(),
                newsDTO.getText(),
                newsDTO.getImage(),
                newsDTO.getCreationTime(),
                newsDTO.getAuthor(),
                newsDTO.getCategory(),
                newsDTO.getComments()
        );
    }
}

