package com.example.Easy.mappers;

import com.example.Easy.entities.CategoryEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.CategoryDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.RecordsDTO;
import com.example.Easy.models.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class RecordsMapperImpl implements RecordsMapper {

    @Override
    public RecordsDTO toRecordsDTO(RecordsEntity recordsEntity) {
        if (recordsEntity == null)
            return null;
        RecordsDTO recordsDTO = RecordsDTO.builder()
                .recordId(recordsEntity.getRecordId())
                .user(toUserDTO(recordsEntity.getUser()))
                .news(toNewsDTO(recordsEntity.getNews()))
                .repeatedRead(recordsEntity.getRepeatedRead())
                .postlike(recordsEntity.isPostlike())
                .postbookmark(recordsEntity.isPostbookmark())
                .build();
        recordsDTO.setNewsCategory(recordsDTO.getNews().getCategory());
        return recordsDTO;
    }

    private UserDTO toUserDTO(UserEntity user) {
        if (user == null)
            return null;
        return UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .image(user.getImage())
                .build();
    }

    private NewsDTO toNewsDTO(NewsEntity newsEntity) {
        if (newsEntity == null)
            return null;
        return NewsDTO.builder()
                .newsId(newsEntity.getNewsId())
                .title(newsEntity.getTitle())
                .text(newsEntity.getText())
                .category(toCategoryDTO(newsEntity.getCategory()))
                .creationTime(newsEntity.getCreationTime())
                .image(newsEntity.getImage())
                .author(toUserDTO(newsEntity.getAuthor()))
                .build();
    }

    private CategoryDTO toCategoryDTO(CategoryEntity category) {
        if (category == null)
            return null;
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
    }

    @Override
    public RecordsEntity toRecordsEntity(RecordsDTO recordsDTO) {
        RecordsEntity recordsEntity = RecordsEntity.builder()
                .recordId(recordsDTO.getRecordId())
                .user(toUserEntity(recordsDTO.getUser()))
                .news(toNewsEntity(recordsDTO.getNews()))
                .repeatedRead(recordsDTO.getRepeatedRead())
                .postlike(recordsDTO.isPostlike())
                .postbookmark(recordsDTO.isPostbookmark())
                .build();
        return null;
    }
    private NewsEntity toNewsEntity(NewsDTO newsDTO) {
        if (newsDTO == null)
            return null;
        return NewsEntity.builder()
                .newsId(newsDTO.getNewsId())
                .author(toUserEntity(newsDTO.getAuthor()))
                .text(newsDTO.getText())
                .title(newsDTO.getTitle())
                .creationTime(newsDTO.getCreationTime())
                .category(toCategoryEntity(newsDTO.getCategory()))
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
    private CategoryEntity toCategoryEntity(CategoryDTO category) {
        if (category == null)
            return null;
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
        return categoryEntity;
    }
}
