package com.example.Easy.Mappers;


import com.example.Easy.Entities.NotificationEntity;
import com.example.Easy.Models.NotificationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface NotificationMapper {
    NotificationEntity toNotificationEntity(NotificationDTO notificationDTO);
    NotificationDTO toNotificationDTO(NotificationEntity notificationEntity);


}
