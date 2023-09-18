package com.example.Easy.config;

import com.example.Easy.models.NotificationDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.services.NotificationService;
import com.example.Easy.services.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaListeners {
    private final UserService userService;
    private final NotificationService notificationService;


    @Autowired
    public KafkaListeners(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "Follow", groupId = "groupId")
    private void listener(String data){
        //On recieve it will send notification
        String[] message = data.split(":");
        try {
            UserDTO user = userService.get(UUID.fromString(message[0]));
            NotificationDTO notification = NotificationDTO.builder()
                    .title("New Follower")
                    .topic(user.getUserId().toString())
                    .text(message[1]+" has followed you")
                    .build();
            notificationService.sendNotificationByTopic(notification);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
    }
}
