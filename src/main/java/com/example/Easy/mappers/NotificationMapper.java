package com.example.Easy.mappers;

import com.example.Easy.entities.NotificationEntity;
import com.example.Easy.models.NotificationDTO;


public interface NotificationMapper {
    NotificationDTO toNotificationDTO(NotificationEntity notificationEntity);
    NotificationEntity toNotificationEntity(NotificationDTO notificationDTO);
}
