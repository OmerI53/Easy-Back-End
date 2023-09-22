package com.example.Easy.mappers;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.models.CommentDTO;


public interface CommentMapper {

    CommentDTO toCommentDTO(CommentEntity commentEntity);
    CommentEntity toCommentEntity(CommentDTO commentDTO);

}
