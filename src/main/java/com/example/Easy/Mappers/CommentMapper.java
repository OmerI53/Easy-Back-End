package com.example.Easy.Mappers;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Models.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {

    CommentEntity toCommentEntity(CommentDTO commentDTO);

    CommentDTO toCommentDTO(CommentEntity commentEntity);

}
