package com.example.Easy.Mappers;

import com.example.Easy.Entities.NotificationEntity;
import com.example.Easy.Models.NotificationDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-30T11:57:14+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationDTO toNotificationDTO(NotificationEntity notificationEntity) {
        if ( notificationEntity == null ) {
            return null;
        }

        NotificationDTO.NotificationDTOBuilder notificationDTO = NotificationDTO.builder();

        notificationDTO.notificationID( notificationEntity.getNotificationID() );
        notificationDTO.userToken( notificationEntity.getUserToken() );
        notificationDTO.topic( notificationEntity.getTopic() );
        notificationDTO.title( notificationEntity.getTitle() );
        notificationDTO.image( notificationEntity.getImage() );
        notificationDTO.text( notificationEntity.getText() );

        return notificationDTO.build();
    }

    @Override
    public NotificationEntity toNotificationEntity(NotificationDTO notificationDTO) {
        if ( notificationDTO == null ) {
            return null;
        }

        NotificationEntity.NotificationEntityBuilder notificationEntity = NotificationEntity.builder();

        notificationEntity.notificationID( notificationDTO.getNotificationID() );
        notificationEntity.userToken( notificationDTO.getUserToken() );
        notificationEntity.topic( notificationDTO.getTopic() );
        notificationEntity.title( notificationDTO.getTitle() );
        notificationEntity.image( notificationDTO.getImage() );
        notificationEntity.text( notificationDTO.getText() );

        return notificationEntity.build();
    }
}
