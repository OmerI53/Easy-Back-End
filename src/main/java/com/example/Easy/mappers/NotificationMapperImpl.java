package com.example.Easy.mappers;

import com.example.Easy.entities.NotificationEntity;
import com.example.Easy.models.NotificationDTO;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapperImpl implements NotificationMapper {
    @Override
    public NotificationDTO toNotificationDTO(NotificationEntity notificationEntity) {
        if (notificationEntity == null)
            return null;
        return NotificationDTO.builder()
                .topic(notificationEntity.getTopic())
                .text(notificationEntity.getText())
                .notificationID(notificationEntity.getNotificationID())
                .title(notificationEntity.getTitle())
                .build();
    }

    @Override
    public NotificationEntity toNotificationEntity(NotificationDTO notificationDTO) {
        if (notificationDTO == null)
            return null;
        return NotificationEntity.builder()
                .text(notificationDTO.getText())
                .title(notificationDTO.getTitle())
                .topic(notificationDTO.getTopic())
                .notificationID(notificationDTO.getNotificationID())
                .build();
    }
}
