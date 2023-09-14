package com.example.Easy.mappers;

import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.models.RecordsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RecordsMapper {
    RecordsEntity toRecordsEntity(RecordsDTO recordsDTO);
    RecordsDTO toRecordsDTO(RecordsEntity recordsEntity);
}
