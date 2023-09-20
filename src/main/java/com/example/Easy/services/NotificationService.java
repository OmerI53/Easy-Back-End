package com.example.Easy.services;

import com.example.Easy.dao.NotificationDao;
import com.example.Easy.entities.NotificationEntity;
import com.example.Easy.mappers.NotificationMapper;
import com.example.Easy.models.NotificationDTO;
import com.example.Easy.repository.NotificationRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationDao {

    private  final NotificationRepository notificationRepository;
    private  final NotificationMapper notificationMapper;

    private final FirebaseMessaging firebaseMessaging;

    public void sendNotificationByToken(NotificationDTO notificationDTO) throws FirebaseMessagingException {
        if(notificationDTO.getUserToken()==null) {
            throw new NullPointerException("User token cannot be null");
        }
        NotificationEntity notificationEntity = NotificationMapper.toNotificationEntity(notificationDTO);
        notificationRepository.save(notificationEntity);
        //build notification from notificationDTO
        Notification notification = Notification.builder()
                .setTitle(notificationDTO.getTitle())
                .setBody(notificationDTO.getText())
                .setImage(notificationDTO.getImage())
                .build();
        //build message by using notification and a recipient token
        Message message = Message.builder()
                .setToken(notificationDTO.getUserToken())
                .setNotification(notification)
                .build();
        //firebase handles the sending procedure
        firebaseMessaging.send(message);
    }


    public void sendNotificationByTopic(NotificationDTO notificationDTO) throws FirebaseMessagingException {
        if (notificationDTO.getTopic() == null) {
            throw new NullPointerException("Topic cannot be null");
        }

        NotificationEntity notificationEntity = NotificationMapper.toNotificationEntity(notificationDTO);
        notificationRepository.save(notificationEntity);

        Notification notification = Notification.builder()
                .setTitle(notificationDTO.getTitle())
                .setBody(notificationDTO.getText())
                .setImage(notificationDTO.getImage())
                .build();

        Message message = Message.builder()
                .setTopic(notificationDTO.getTopic())
                .setNotification(notification)
                .build();

        firebaseMessaging.send(message);
    }

    public void subscribeToTopic(String topic, String token) throws FirebaseMessagingException {
        firebaseMessaging.subscribeToTopic(Collections.singletonList(token),topic);
    }

    public void unsubscribeToTopic(String topic, String token) throws FirebaseMessagingException {
        firebaseMessaging.unsubscribeFromTopic(Collections.singletonList(token),topic);
    }

    public List<NotificationDTO> get(String title) {
        return notificationRepository.getNotificationByTitle(title)
                .stream()
                .map(NotificationMapper::toNotificationDTO)
                .collect(Collectors.toList());
    }
}
