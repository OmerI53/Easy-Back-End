package com.example.Easy.services;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @Test
    void sendNotificationByToken() throws FirebaseMessagingException {
        /*
        String toke = "d4NqQ6cNQTeTeyokKcE9ey:APA91bG_6P5HhaOjvxNqyT5KBhhfDeGypPmnaz181XA7llg5unXXXyj5o47JKq-5ZEkULgwrgljfrxiT-We3XzbQBWcbKyaFC9T75hIg6SwwXhbgx_2aue8bFmbiiVf_m-Qj2ivcwqOi";
                NotificationDTO notificationDTO = NotificationDTO.builder()
                .userToken(toke)
                .title("Notificaiton")
                .text("By Omer")
                .image("...")
                .build();
        String response = notificationService.sendNotificationByToken(notificationDTO);
        System.out.println(response);

         */
    }

    @Test
    void subscribteToTopic() throws FirebaseMessagingException {
        /*
        String token ="fgiMSYssSZu_i4DjI3wxY5:APA91bH5Ylp92cYdZVBHoEqozkbBQ1-EGQI2JMTdmReLeMUFPqNsq1T8OIvBDasBGZjI_aFTeEhQU2lAtqGKVjong8YIUvBckiHvn-GjgVbvF9I0Ih1y316xtARCr_Ys50w8HE-Fy5nC";
        notificationService.subscribeToTopic("MACHINE_LEARNING",token);

         */
    }
    /*
    @Test
    void sendNotificationByTopic() throws FirebaseMessagingException {
        String token ="fgiMSYssSZu_i4DjI3wxY5:APA91bH5Ylp92cYdZVBHoEqozkbBQ1-EGQI2JMTdmReLeMUFPqNsq1T8OIvBDasBGZjI_aFTeEhQU2lAtqGKVjong8YIUvBckiHvn-GjgVbvF9I0Ih1y316xtARCr_Ys50w8HE-Fy5nC";
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .image("img")
                .text("sent by topic")
                .title("Topic Messaging")
                .topic("MACHINE_LEARNING")
                .build();
        notificationService.sendNotificationByTopic(notificationDTO);
    }

 */

}