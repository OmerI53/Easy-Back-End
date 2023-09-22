package com.example.Easy.mappers;

import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.models.RecordsDTO;

public interface RecordsMapper {
    RecordsEntity toRecordsEntity(RecordsDTO recordsDTO);
    RecordsDTO toRecordsDTO(RecordsEntity recordsEntity);

}
