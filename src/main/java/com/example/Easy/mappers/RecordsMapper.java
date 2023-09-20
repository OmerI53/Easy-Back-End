package com.example.Easy.mappers;

import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.models.RecordsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RecordsMapper {
    public static RecordsEntity toRecordsEntity(RecordsDTO recordsDTO){
        return new RecordsEntity(
                recordsDTO.getRecordId(),
                recordsDTO.getUser(),
                recordsDTO.getNewsCategory(),
                recordsDTO.getNews(),
                recordsDTO.getRepeatedRead()
        );
    }
    public static RecordsDTO toRecordsDTO(RecordsEntity recordsEntity){
        return new RecordsDTO(
                recordsEntity.getRecordID(),
                recordsEntity.getUser(),
                recordsEntity.getNewsCategory(),
                recordsEntity.getNews(),
                recordsEntity.getRepeatedRead()
        );
    }
}

