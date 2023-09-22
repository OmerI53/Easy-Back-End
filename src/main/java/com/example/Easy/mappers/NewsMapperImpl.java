package com.example.Easy.mappers;

import com.example.Easy.entities.CategoryEntity;
import com.example.Easy.entities.CommentEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.CategoryDTO;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
@Component
public class NewsMapperImpl implements NewsMapper {
    @Override
    public NewsDTO toNewsDTO(NewsEntity newsEntity) {
        if(newsEntity==null)
            return null;

        return NewsDTO.builder()
                .newsId(newsEntity.getNewsId())
                .author(toUserDTO(newsEntity.getAuthor()))
                .image(newsEntity.getImage())
                .title(newsEntity.getTitle())
                .text(newsEntity.getText())
                .creationTime(newsEntity.getCreationTime())
                .category(toCategoryDTO(newsEntity.getCategory()))
                .comments(commentsListMapper(newsEntity.getComments()))
                .postBookmarks(newsEntity.getPostBookmarks())
                .postLikes(newsEntity.getPostLikes())
                .postViews(newsEntity.getPostViews())
                .build();
    }

    private List<CommentDTO> commentsListMapper(List<CommentEntity> comments) {
        if(comments==null)
            return null;
    List<CommentDTO> commentDTOS = new LinkedList<>();
    for(CommentEntity commentEntity:comments)
        commentDTOS.add(toCommentDTO(commentEntity));
    return commentDTOS;
    }

    private CommentDTO toCommentDTO(CommentEntity commentEntity) {
        if(commentEntity==null)
            return null;
    return CommentDTO.builder()
            .commentId(commentEntity.getCommentId())
            .build();
    }

    private CategoryDTO toCategoryDTO(CategoryEntity category) { //news might be needed
        if(category==null)
            return null;
    return CategoryDTO.builder()
            .name(category.getName())
            .categoryId(category.getCategoryId())
            .build();
    }

    private UserDTO toUserDTO(UserEntity author) {
        if(author==null)
            return null;
        return UserDTO.builder()
                .userId(author.getUserId())
                .email(author.getEmail())
                .name(author.getName())
                .image(author.getImage())
                .build();
    }

    @Override
    public NewsEntity toNewsEntity(NewsDTO newsDTO) {
        if(newsDTO==null)
            return null;
        return NewsEntity.builder()
                .newsId(newsDTO.getNewsId())
                .author(toUserEntity(newsDTO.getAuthor()))
                .image(newsDTO.getImage())
                .title(newsDTO.getTitle())
                .text(newsDTO.getText())
                .creationTime(newsDTO.getCreationTime())
                .category(toCategoryEntity(newsDTO.getCategory()))
                .comments(commentsListMapperEntity(newsDTO.getComments()))
                .postBookmarks(newsDTO.getPostBookmarks())
                .postLikes(newsDTO.getPostLikes())
                .postViews(newsDTO.getPostViews())
                .build();
    }

    private List<CommentEntity> commentsListMapperEntity(List<CommentDTO> comments) {
        if(comments==null)
            return null;
    List<CommentEntity> commentEntities = new LinkedList<>();
    for(CommentDTO commentDTO:comments)
        commentEntities.add(toCommentEntity(commentDTO));
    return commentEntities;
    }

    private CommentEntity toCommentEntity(CommentDTO commentDTO) {
    if(commentDTO==null)
        return null;
    return CommentEntity.builder()
            .commentId(commentDTO.getCommentId())
            .text(commentDTO.getText())
            .creationTime(commentDTO.getCreationTime())
            .build();
    }

    private CategoryEntity toCategoryEntity(CategoryDTO category) {
    if(category==null)
        return null;
    return CategoryEntity.builder()
            .categoryId(category.getCategoryId())
            .name(category.getName())
            .build();
    }

    private UserEntity toUserEntity(UserDTO author) {
        if(author==null)
            return null;
        return UserEntity.builder()
                .userId(author.getUserId())
                .name(author.getName())
                .email(author.getEmail())
                .build();
    }

}
