package com.example.Easy.mappers;

import com.example.Easy.entities.*;
import com.example.Easy.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDTO toUserDTO(UserEntity userEntity) {
        if (userEntity == null)
            return null;

        UserDTO userDTO = UserDTO.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .image(userEntity.getImage())
                .devices(deviceListMapper(userEntity.getDevices()))
                .followers(userSetMapper(userEntity.getFollowers()))
                .following(userSetMapper(userEntity.getFollowing()))
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
        userDTO.setNews(newsListMapper(userEntity.getNews(), userDTO));
        userDTO.setComments(commentsListMapper(userEntity.getComments(), userDTO));
        return userDTO;
    }

    private List<CommentDTO> commentsListMapper(List<CommentEntity> comments, UserDTO userDTO) {
        if (comments == null)
            return null;
        List<CommentDTO> commentDTOS = new LinkedList<>();
        for (CommentEntity commentEntity : comments)
            commentDTOS.add(toCommentDTO(commentEntity, userDTO));
        return commentDTOS;
    }

    private CommentDTO toCommentDTO(CommentEntity commentEntity, UserDTO userDTO) {
        if (commentEntity == null)
            return null;
        return CommentDTO.builder()
                .commentId(commentEntity.getCommentId())
                .news(toNewsDTO(commentEntity.getNews(), toOtherUsersDTO(commentEntity.getNews().getAuthor())))
                .author(userDTO)
                .text(commentEntity.getText())
                .build();
    }

    private Set<UserDTO> userSetMapper(Set<UserEntity> followers) {
        if (followers == null)
            return null;
        Set<UserDTO> userDTOS = new HashSet<>();
        for (UserEntity userEntity : followers)
            userDTOS.add(toOtherUsersDTO(userEntity));
        return userDTOS;
    }

    private UserDTO toOtherUsersDTO(UserEntity userEntity) {
        if (userEntity == null)
            return null;
        return UserDTO.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .image(userEntity.getImage())
                .build();
    }

    private List<NewsDTO> newsListMapper(List<NewsEntity> news, UserDTO userDTO) {
        if (news == null)
            return null;
        List<NewsDTO> newsDTOS = new LinkedList<>();
        for (NewsEntity newsEntity : news)
            newsDTOS.add(toNewsDTO(newsEntity, userDTO));
        return newsDTOS;
    }

    private NewsDTO toNewsDTO(NewsEntity newsEntity, UserDTO userDTO) {
        if (newsEntity == null)
            return null;
        //is title,text etc. needed ?
        return NewsDTO.builder()
                .newsId(newsEntity.getNewsId())
                .title(newsEntity.getTitle())
                .text(newsEntity.getText())
                .category(toCategoryDTO(newsEntity.getCategory()))
                .creationTime(newsEntity.getCreationTime())
                .image(newsEntity.getImage())
                .author(userDTO)
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

    private List<DeviceDTO> deviceListMapper(List<DeviceEntity> devices) {
        if (devices == null)
            return null;
        List<DeviceDTO> deviceDTOS = new LinkedList<>();
        for (DeviceEntity device : devices) {
            deviceDTOS.add(toDeviceDTO(device));
        }
        return deviceDTOS;
    }

    private DeviceDTO toDeviceDTO(DeviceEntity device) {
        if (device == null)
            return null;
        return DeviceDTO.builder()
                .deviceID(device.getDeviceID())
                .deviceToken(device.getDeviceToken())
                .deviceType(device.getDeviceType())
                .timeZone(device.getTimeZone())
                .build();
    }

    @Override
    public UserEntity toUserEntity(UserDTO userDTO) {
        if (userDTO == null)
            return null;
        UserEntity userEntity = UserEntity.builder()
                .userId(userDTO.getUserId())
                .name(userDTO.getName())
                .image(userDTO.getImage())
                .followers(userSetMapperEntity(userDTO.getFollowers()))
                .following(userSetMapperEntity(userDTO.getFollowing()))
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
        userEntity.setNews(newsListMapperEntity(userDTO.getNews(), userEntity));
        userEntity.setDevices(deviceListMapperEntity(userDTO.getDevices(), userEntity));
        userEntity.setComments(commentsListMapperEntity(userDTO.getComments(), userEntity));


        return userEntity;
    }

    private List<CommentEntity> commentsListMapperEntity(List<CommentDTO> comments, UserEntity userEntity) {
        if (comments == null)
            return null;
        List<CommentEntity> commentEntities = new LinkedList<>();
        for (CommentDTO commentDTO : comments)
            commentEntities.add(toCommentEntity(commentDTO, userEntity));
        return commentEntities;
    }

    private CommentEntity toCommentEntity(CommentDTO commentDTO, UserEntity userEntity) {
        if (commentDTO == null)
            return null;
        return CommentEntity.builder()
                .commentId(commentDTO.getCommentId())
                .text(commentDTO.getText())
                .author(userEntity)
                .creationTime(commentDTO.getCreationTime())
                .build();
    }

    private List<NewsEntity> newsListMapperEntity(List<NewsDTO> news, UserEntity userEntity) {
        if (news == null)
            return null;
        List<NewsEntity> newsEntities = new LinkedList<>();
        for (NewsDTO newsDTO : news)
            newsEntities.add(toNewsEntity(newsDTO, userEntity));
        return newsEntities;
    }

    private NewsEntity toNewsEntity(NewsDTO newsDTO, UserEntity userEntity) {
        if (newsDTO == null)
            return null;
        return NewsEntity.builder()
                .newsId(newsDTO.getNewsId())
                .author(userEntity)
                .text(newsDTO.getText())
                .title(newsDTO.getTitle())
                .creationTime(newsDTO.getCreationTime())
                .category(toCategoryEntity(newsDTO.getCategory()))
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

    private Set<UserEntity> userSetMapperEntity(Set<UserDTO> followers) {
        if (followers == null)
            return null;
        Set<UserEntity> userEntities = new HashSet<>();
        for (UserDTO userDTO : followers)
            userEntities.add(toOtherUsersEntity(userDTO));
        return userEntities;
    }

    private UserEntity toOtherUsersEntity(UserDTO userDTO) {
        if (userDTO == null)
            return null;
        UserEntity userEntity = UserEntity.builder()
                .userId(userDTO.getUserId())
                .build();
        return userEntity;
    }

    private List<DeviceEntity> deviceListMapperEntity(List<DeviceDTO> devices, UserEntity userEntity) {
        if (devices == null)
            return null;
        List<DeviceEntity> deviceEntities = new LinkedList<>();
        for (DeviceDTO device : devices) {
            deviceEntities.add(toDeviceEntity(device, userEntity));
        }
        return deviceEntities;

    }

    private DeviceEntity toDeviceEntity(DeviceDTO device, UserEntity userEntity) {
        if (device == null)
            return null;
        DeviceEntity deviceEntity = DeviceEntity.builder()
                .deviceToken(device.getDeviceToken())
                .deviceType(device.getDeviceType())
                .deviceID(device.getDeviceID())
                .timeZone(device.getTimeZone())
                .build();
        return deviceEntity;
    }
}
