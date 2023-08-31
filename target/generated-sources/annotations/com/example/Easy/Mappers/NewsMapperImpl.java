package com.example.Easy.Mappers;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Models.NewsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-30T11:57:14+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsDTO toNewsDTO(NewsEntity newsEntity) {
        if ( newsEntity == null ) {
            return null;
        }

        NewsDTO.NewsDTOBuilder newsDTO = NewsDTO.builder();

        newsDTO.newsUUID( newsEntity.getNewsUUID() );
        newsDTO.title( newsEntity.getTitle() );
        newsDTO.text( newsEntity.getText() );
        newsDTO.image( newsEntity.getImage() );
        newsDTO.author( newsEntity.getAuthor() );
        newsDTO.creationTime( newsEntity.getCreationTime() );
        newsDTO.category( newsEntity.getCategory() );
        List<CommentEntity> list = newsEntity.getComments();
        if ( list != null ) {
            newsDTO.comments( new ArrayList<CommentEntity>( list ) );
        }

        return newsDTO.build();
    }

    @Override
    public NewsEntity toNewsEntity(NewsDTO newsDTO) {
        if ( newsDTO == null ) {
            return null;
        }

        NewsEntity.NewsEntityBuilder newsEntity = NewsEntity.builder();

        newsEntity.newsUUID( newsDTO.getNewsUUID() );
        newsEntity.title( newsDTO.getTitle() );
        newsEntity.text( newsDTO.getText() );
        newsEntity.image( newsDTO.getImage() );
        newsEntity.creationTime( newsDTO.getCreationTime() );
        newsEntity.author( newsDTO.getAuthor() );
        newsEntity.category( newsDTO.getCategory() );
        List<CommentEntity> list = newsDTO.getComments();
        if ( list != null ) {
            newsEntity.comments( new ArrayList<CommentEntity>( list ) );
        }

        return newsEntity.build();
    }
}
