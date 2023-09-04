package com.example.Easy.Mappers;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Models.CommentDTO;


public interface CommentMapper {

    CommentEntity toCommentEntity(CommentDTO commentDTO);

    CommentDTO toCommentDTO(CommentEntity commentEntity);

}
