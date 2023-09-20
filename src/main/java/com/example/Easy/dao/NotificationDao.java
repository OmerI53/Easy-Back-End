package com.example.Easy.dao;

import com.example.Easy.models.NotificationDTO;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

public interface NotificationDao {
    public void sendNotificationByToken(NotificationDTO notificationDTO)throws FirebaseMessagingException;

    public void sendNotificationByTopic(NotificationDTO notificationDTO)throws FirebaseMessagingException;

    public void subscribeToTopic(String topic, String token)throws FirebaseMessagingException;

    public void unsubscribeToTopic(String topic, String token)throws FirebaseMessagingException;
    public List<NotificationDTO> get(String title);
}
