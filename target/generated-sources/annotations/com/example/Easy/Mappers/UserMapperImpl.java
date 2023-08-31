package com.example.Easy.Mappers;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Models.UserDTO;
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
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toUserEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.userId( userDTO.getUserId() );
        userEntity.name( userDTO.getName() );
        userEntity.image( userDTO.getImage() );
        userEntity.userToken( userDTO.getUserToken() );
        List<NewsEntity> list = userDTO.getNews();
        if ( list != null ) {
            userEntity.news( new ArrayList<NewsEntity>( list ) );
        }
        List<CommentEntity> list1 = userDTO.getComments();
        if ( list1 != null ) {
            userEntity.comments( new ArrayList<CommentEntity>( list1 ) );
        }
        userEntity.role( userDTO.getRole() );

        return userEntity.build();
    }

    @Override
    public UserDTO toUserDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.userId( userEntity.getUserId() );
        userDTO.name( userEntity.getName() );
        userDTO.image( userEntity.getImage() );
        userDTO.userToken( userEntity.getUserToken() );
        userDTO.role( userEntity.getRole() );
        List<CommentEntity> list = userEntity.getComments();
        if ( list != null ) {
            userDTO.comments( new ArrayList<CommentEntity>( list ) );
        }
        List<NewsEntity> list1 = userEntity.getNews();
        if ( list1 != null ) {
            userDTO.news( new ArrayList<NewsEntity>( list1 ) );
        }

        return userDTO.build();
    }
}
