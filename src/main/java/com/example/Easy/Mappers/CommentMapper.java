package com.example.Easy.Mappers;

import com.example.Easy.Entites.CommentEntity;
import com.example.Easy.Models.CommentDTO;
import org.springframework.context.annotation.Bean;


public interface CommentMapper {

    CommentEntity toCommentEntity(CommentDTO commentDTO);

    CommentDTO toCommentDTO(CommentEntity commentEntity);

}
