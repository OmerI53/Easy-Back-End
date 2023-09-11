package com.example.Easy.Mappers;

import com.example.Easy.Entities.RecordsEntity;
import com.example.Easy.Models.RecordsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RecordsMapper {
    RecordsEntity toRecordsEntity(RecordsDTO recordsDTO);
    RecordsDTO toRecordsDTO(RecordsEntity recordsEntity);
}
