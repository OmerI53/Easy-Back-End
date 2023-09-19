package com.example.Easy.mappers;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import org.mapstruct.*;

@Named("UserMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    //-----Mappers for UserDTO -----
    @Named("toUserDTO")
    @Mappings(value = {
            @Mapping(target = "news", qualifiedByName = "newsMapper"),
            @Mapping(target = "devices", qualifiedByName = "deviceMapper"),
            @Mapping(target = "followers", qualifiedByName = "followMapper"),
            @Mapping(target = "following", qualifiedByName = "followMapper"),
            @Mapping(target = "userRecords", ignore = true),
    })
    UserDTO toUserDTO(UserEntity userEntity);


    @Named("newsMapper")
    @Mappings(value = {
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "newsRecord", ignore = true),
            @Mapping(target = "comments", ignore = true)
    })
    NewsDTO toNewsDTOforUsers(NewsEntity newsEntity);

    @Named("deviceMapper")
    @Mappings(value = {
            @Mapping(target = "users", ignore = true)
    })
    DeviceDTO toDeviceDTOforUsers(DeviceEntity deviceEntity);

    @Named("followMapper")
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
    UserDTO followMappers(UserEntity userEntity);

    //-----User-Entity-----
    @Named("toUserEntity")
    @Mappings(value = {
            @Mapping(target = "followers", qualifiedByName = "followMapperEntity"),
            @Mapping(target = "following", qualifiedByName = "followMapperEntity"),

    })
    UserEntity toUserEntity(UserDTO userDTO);

    @AfterMapping
    default void afterToEntity(UserDTO userDTO, @MappingTarget UserEntity.UserEntityBuilder userEntityBuilder) {

    }

    @Named("followMapperEntity")
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
    UserEntity followMappers(UserDTO userDTO);

    default UserEntity userDTOToUserEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.userId( userDTO.getUserId() );
        userEntity.name( userDTO.getName() );
        userEntity.image( userDTO.getImage() );
        userEntity.email( userDTO.getEmail() );
        userEntity.password( userDTO.getPassword() );
        userEntity.role( userDTO.getRole() );

        return userEntity.build();
    }

    default CommentDTO commentEntityToCommentDTO(CommentEntity commentEntity) {
        if (commentEntity == null) {
            return null;
        }
        CommentDTO.CommentDTOBuilder commentDTO = CommentDTO.builder();
        commentDTO.commentId(commentEntity.getCommentId());
        commentDTO.text(commentEntity.getText());
        return commentDTO.build();
    }

}
