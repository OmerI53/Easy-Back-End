package com.example.Easy.mappers;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public CommentDTO toCommentDTO(CommentEntity commentEntity) {
        if (commentEntity == null)
            return null;
        return CommentDTO.builder()
                .commentId(commentEntity.getCommentId())
                .text(commentEntity.getText())
                .author(toUserDTO(commentEntity.getAuthor()))
                .news(toNewsDTO(commentEntity.getNews()))
                .creationTime(commentEntity.getCreationTime())
                .build();
    }

    private NewsDTO toNewsDTO(NewsEntity news) {
        if (news == null)
            return null;

        return NewsDTO.builder()
                .newsId(news.getNewsId())
                .title(news.getTitle())
                .creationTime(news.getCreationTime())
                .build();
    }

    private UserDTO toUserDTO(UserEntity author) {
        if (author == null)
            return null;
        return UserDTO.builder()
                .name(author.getName())
                .image(author.getImage())
                .userId(author.getUserId())
                .email(author.getEmail())
                .build();
    }

    @Override
    public CommentEntity toCommentEntity(CommentDTO commentDTO) {
        return null;
    }
}
