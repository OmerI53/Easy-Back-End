package com.example.Easy.Mappers;

import com.example.Easy.Entities.NewsCategoryEntity;
import com.example.Easy.Models.NewsCategoryDTO;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-29T17:32:52+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class NewsCategoryMapperImpl implements NewsCategoryMapper {

    @Override
    public NewsCategoryEntity toNewsCategoryEntity(NewsCategoryDTO newsCategoryDTO) {
        if ( newsCategoryDTO == null ) {
            return null;
        }

        NewsCategoryEntity newsCategoryEntity = new NewsCategoryEntity();

        newsCategoryEntity.setCategoryId( newsCategoryDTO.getCategoryId() );
        newsCategoryEntity.setName( newsCategoryDTO.getName() );
        newsCategoryEntity.setParent( newsCategoryDTO.getParent() );
        Set<NewsCategoryEntity> set = newsCategoryDTO.getChildren();
        if ( set != null ) {
            newsCategoryEntity.setChildren( new LinkedHashSet<NewsCategoryEntity>( set ) );
        }

        return newsCategoryEntity;
    }

    @Override
    public NewsCategoryDTO toNewsCategoryDTO(NewsCategoryEntity newsEntity) {
        if ( newsEntity == null ) {
            return null;
        }

        NewsCategoryDTO.NewsCategoryDTOBuilder newsCategoryDTO = NewsCategoryDTO.builder();

        newsCategoryDTO.categoryId( newsEntity.getCategoryId() );
        newsCategoryDTO.name( newsEntity.getName() );
        newsCategoryDTO.parent( newsEntity.getParent() );
        Set<NewsCategoryEntity> set = newsEntity.getChildren();
        if ( set != null ) {
            newsCategoryDTO.children( new LinkedHashSet<NewsCategoryEntity>( set ) );
        }

        return newsCategoryDTO.build();
    }
}
