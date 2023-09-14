package com.example.Easy.mappers;


import com.example.Easy.entities.NotificationEntity;
import com.example.Easy.models.NotificationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NotificationMapper {
    NotificationEntity toNotificationEntity(NotificationDTO notificationDTO);
    NotificationDTO toNotificationDTO(NotificationEntity notificationEntity);


}
