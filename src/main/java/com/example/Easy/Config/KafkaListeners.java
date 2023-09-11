package com.example.Easy.Config;

import com.example.Easy.Models.NotificationDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Services.NotificationService;
import com.example.Easy.Services.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaListeners {
    @Autowired
    UserService userService;
    @Autowired
    NotificationService notificationService;
    @KafkaListener(topics = "Follow",
    groupId = "groupId")
    private void listener(String data){
        //On recieve it will send notification
        String[] message = data.split(":");
        UserDTO user = userService.getUserById(UUID.fromString(message[0]));
        NotificationDTO notification = NotificationDTO.builder()
                .title("New Follower")
                .topic(user.getUserId().toString())
                .text(message[1]+" has followed you")
                .build();
        try {
            notificationService.sendNotificationByTopic(notification);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
    }
}
