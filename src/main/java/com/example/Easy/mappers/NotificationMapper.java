package com.example.Easy.mappers;


import com.example.Easy.entities.NotificationEntity;
import com.example.Easy.models.NotificationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NotificationMapper {
    public static NotificationEntity toNotificationEntity(NotificationDTO notificationDTO){
        return new NotificationEntity(
                notificationDTO.getNotificationID(),
                notificationDTO.getUserToken(),
                notificationDTO.getTopic(),
                notificationDTO.getTitle(),
                notificationDTO.getImage(),
                notificationDTO.getText()
        );
    }
    public static NotificationDTO toNotificationDTO(NotificationEntity notificationEntity){
        return new NotificationDTO(
                notificationEntity.getNotificationID(),
                notificationEntity.getUserToken(),
                notificationEntity.getTopic(),
                notificationEntity.getTitle(),
                notificationEntity.getImage(),
                notificationEntity.getText()
        );
    }


}

