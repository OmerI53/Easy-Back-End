package com.example.Easy.mappers;

import com.example.Easy.entities.NewsCategoryEntity;
import com.example.Easy.models.NewsCategoryDTO;
import org.checkerframework.checker.units.qual.N;
import org.mapstruct.Mapper;

@Mapper
public interface NewsCategoryMapper {
    public static NewsCategoryEntity toNewsCategoryEntity(NewsCategoryDTO newsCategoryDTO){
        return new NewsCategoryEntity(
                newsCategoryDTO.getCategoryId(),
                newsCategoryDTO.getName(),
                newsCategoryDTO.getParent()
        );

    }

    public static NewsCategoryDTO toNewsCategoryDTO(NewsCategoryEntity newsCategoryEntity){
        return new NewsCategoryDTO(
                newsCategoryEntity.getCategoryId(),
                newsCategoryEntity.getName(),
                newsCategoryEntity.getParent(),
                newsCategoryEntity.getNews(),
                newsCategoryEntity.getCategoryRecord(),
                newsCategoryEntity.getChildren()
        );

    }

}

