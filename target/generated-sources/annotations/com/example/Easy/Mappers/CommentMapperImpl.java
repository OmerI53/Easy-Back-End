package com.example.Easy.Mappers;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Models.CommentDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-29T17:32:52+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDTO toCommentDTO(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }

        CommentDTO.CommentDTOBuilder commentDTO = CommentDTO.builder();

        commentDTO.commentId( commentEntity.getCommentId() );
        commentDTO.text( commentEntity.getText() );
        commentDTO.author( commentEntity.getAuthor() );
        commentDTO.news( commentEntity.getNews() );

        return commentDTO.build();
    }

    @Override
    public CommentEntity toCommentEntity(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        CommentEntity.CommentEntityBuilder commentEntity = CommentEntity.builder();

        commentEntity.commentId( commentDTO.getCommentId() );
        commentEntity.text( commentDTO.getText() );
        commentEntity.author( commentDTO.getAuthor() );
        commentEntity.news( commentDTO.getNews() );

        return commentEntity.build();
    }
}
