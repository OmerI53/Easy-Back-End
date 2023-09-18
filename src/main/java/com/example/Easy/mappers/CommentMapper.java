package com.example.Easy.mappers;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.models.CommentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {

    CommentEntity toCommentEntity(CommentDTO commentDTO);

    CommentDTO toCommentDTO(CommentEntity commentEntity);

}

