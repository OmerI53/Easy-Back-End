package com.example.Easy.controllers;

import com.example.Easy.models.NotificationDTO;
import com.example.Easy.services.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/post")
    public void postNotificationByToken(@RequestBody NotificationDTO notificationDTO) throws FirebaseMessagingException {
        notificationService.sendNotificationByToken(notificationDTO);
    }

    @PostMapping("/topic")
    public ResponseEntity<?> postNotificationByTopic(@RequestBody NotificationDTO notificationDTO) throws FirebaseMessagingException {
        notificationService.sendNotificationByTopic(notificationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/subscribe/{topic}")
    public ResponseEntity<?> subscribeToTopic(@PathVariable("topic") String topic,@RequestBody String token) throws FirebaseMessagingException {
        notificationService.subscribeToTopic(topic,token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getNotificationByTopic(@PathVariable("title") String title) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(notificationService.getMessageByTitle(title));
    }


}