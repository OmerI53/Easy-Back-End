package com.example.Easy.mappers;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.models.CommentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {

    public static CommentDTO toCommentDTO(CommentEntity commentEntity){
        return new CommentDTO(
                commentEntity.getCommentId(),
                commentEntity.getText(),
                commentEntity.getAuthor(),
                commentEntity.getNews()
        );
    }
    public static CommentEntity toCommentEntity(CommentDTO commentDTO){
        return new CommentEntity(
                commentDTO.getCommentId(),
                commentDTO.getText(),
                commentDTO.getAuthor(),
                commentDTO.getNewsId()
        );
    }


}

