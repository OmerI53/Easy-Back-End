package com.example.Easy.mappers;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper
public interface CommentMapper {
    @Mappings(value = {
            @Mapping(target = "author",qualifiedByName = "userMapper"),
            @Mapping(target = "news",qualifiedByName = "newsMapper")
    })
    CommentDTO toCommentDTO(CommentEntity commentEntity);

    @Mappings(value = {
            @Mapping(target = "news",qualifiedByName = "newsMapperEntity")
    })
    CommentEntity toCommentEntity(CommentDTO commentDTO);

    @Named("userMapper")
    @Mappings(value = {
            @Mapping(target = "news",ignore = true),
            @Mapping(target = "comments",ignore = true),
            @Mapping(target = "following",ignore = true),
            @Mapping(target = "followers",ignore = true),
            @Mapping(target = "userRecords",ignore = true),
            @Mapping(target = "email",ignore = true),
            @Mapping(target = "password",ignore = true),
            @Mapping(target = "devices",ignore = true),
    })
    UserDTO userMapper(UserEntity userEntity);


    @Named("newsMapper")
    @Mappings(value = {
            @Mapping(target = "author",ignore = true),
            @Mapping(target ="newsRecord",ignore = true),
            @Mapping(target = "comments",ignore = true)
    })
    NewsDTO newsMapper(NewsEntity newsEntity);


    @Named("newsMapperEntity")
    @Mappings(value = {
            @Mapping(target = "author",ignore = true),
            @Mapping(target ="newsRecord",ignore = true),
            @Mapping(target = "comments",ignore = true)
    })
    NewsEntity newsMapper(NewsDTO newsDTO);
}
