package com.example.Easy.config;

import com.example.Easy.requests.CreateNotificationRequest;
import com.example.Easy.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final NotificationService notificationService;
    private final ResourceBundleMessageSource source;

    @KafkaListener(topics = "Follow",
    groupId = "groupId")
    private void listener(String data){
        //On recieve it will send notification
        System.out.println("kafka");
        String[] message = data.split(":");
        CreateNotificationRequest request = CreateNotificationRequest.builder()
                .title("New Follower")
                .topic(message[0])
                .text(message[1]+source.getMessage("notification.follow",null, LocaleContextHolder.getLocale()))
                .build();
        notificationService.sendNotification(request);

        System.out.println(data);
    }
}
