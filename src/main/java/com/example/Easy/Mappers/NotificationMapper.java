package com.example.Easy.Mappers;


import com.example.Easy.Entities.NotificationEntity;
import com.example.Easy.Models.NotificationDTO;

public interface NotificationMapper {
    NotificationEntity toNotificationEntity(NotificationDTO notificationDTO);
    NotificationDTO toNotificationDto(NotificationEntity notificationEntity);


}
