package com.example.Easy.mappers;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import org.mapstruct.*;

@Named("NewsMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {
    @Mappings(value = {
            @Mapping(target = "author", qualifiedByName = "userMapper"),
            @Mapping(target = "newsRecord", ignore = true)
    })
    NewsDTO toNewsDTO(NewsEntity newsEntity);


    NewsEntity toNewsEntity(NewsDTO newsDTO);


    @Named("userMapper")
    @Mappings(value = {
            @Mapping(target = "news", ignore = true),
            @Mapping(target = "comments", ignore = true),
            @Mapping(target = "following", ignore = true),
            @Mapping(target = "followers", ignore = true),
            @Mapping(target = "userRecords", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "devices", ignore = true)
    })
    UserDTO toUserDTOforNews(UserEntity userEntity);


    default CommentEntity commentDTOToCommentEntity(CommentDTO commentDTO) {
        if (commentDTO == null) {
            return null;
        }

        CommentEntity.CommentEntityBuilder commentEntity = CommentEntity.builder();
        commentEntity.commentId(commentDTO.getCommentId());
        commentEntity.text(commentDTO.getText());

        return commentEntity.build();
    }

    default CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity) {
        if (commentEntity == null) {
            return null;
        }

        CommentDTO.CommentDTOBuilder commentDTO = CommentDTO.builder();
        commentDTO.author(UserDTO.builder()
                .name(commentEntity.getAuthor().getName())
                .image(commentEntity.getAuthor().getImage())
                .userId(commentEntity.getAuthor().getUserId())
                .build());
        commentDTO.creationTime(commentEntity.getCreationTime());
        commentDTO.commentId(commentEntity.getCommentId());
        commentDTO.text(commentEntity.getText());

        return commentDTO.build();
    }

}
