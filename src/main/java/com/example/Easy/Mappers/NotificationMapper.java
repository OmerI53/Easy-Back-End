package com.example.Easy.Mappers;


import com.example.Easy.Entites.DeviceEntity;
import com.example.Easy.Entites.NotificationEntity;
import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Models.NotificationDTO;

public interface NotificationMapper {
    NotificationEntity toNotificationEntity(NotificationDTO notificationDTO);
    NotificationDTO toNotificationDto(NotificationEntity notificationEntity);


}
