package com.example.Easy.mappers;

import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.RecordsDTO;
import com.example.Easy.models.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Named("RecordsMapper")
@Mapper
public interface RecordsMapper {
    RecordsEntity toRecordsEntity(RecordsDTO recordsDTO);
    @Mappings(value = {
            @Mapping(target = "news",qualifiedByName = "newsMapper"),
            @Mapping(target = "user",qualifiedByName = "userMapper"),
    })
    RecordsDTO toRecordsDTO(RecordsEntity recordsEntity);

    @Named("newsMapper")
    @Mappings(value = {
            @Mapping(target = "author",ignore = true),
            @Mapping(target ="newsRecord",ignore = true),
            @Mapping(target = "comments",ignore = true)
    })
    NewsDTO toNewsDTOforUsers(NewsEntity newsEntity);
    @Named("userMapper")
    @Mappings(value = {
            @Mapping(target = "news",ignore = true),
            @Mapping(target = "comments",ignore = true),
            @Mapping(target = "following",ignore = true),
            @Mapping(target = "followers",ignore = true),
            @Mapping(target = "userRecords",ignore = true),
            @Mapping(target = "email",ignore = true),
            @Mapping(target = "password",ignore = true),
            @Mapping(target="devices",ignore = true)
    })
    UserDTO toUserDTOforNews(UserEntity userEntity);

}
