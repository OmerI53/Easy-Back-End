package com.example.Easy.Mappers;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Models.CommentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {
    CommentDTO toCommentDTO(CommentEntity commentEntity);
    CommentEntity toCommentEntity(CommentDTO commentDTO);
}
