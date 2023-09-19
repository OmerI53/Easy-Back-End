package com.example.Easy.controllers;

import com.example.Easy.models.response.NotificationResponse;
import com.example.Easy.models.response.NotificationSubsResponse;
import com.example.Easy.requests.CreateNotificationRequest;
import com.example.Easy.requests.CreateSubscriptionRequest;
import com.example.Easy.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> postNotificationByToken(@RequestBody CreateNotificationRequest createNotificationRequest) {
        return new ResponseEntity<NotificationResponse>(new NotificationResponse(notificationService.sendNotification(createNotificationRequest)), HttpStatus.CREATED);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<NotificationSubsResponse> subscribeToTopic(@RequestBody CreateSubscriptionRequest createSubscriptionRequest) {

        return new ResponseEntity<>(new NotificationSubsResponse(notificationService.subscribeToTopic(createSubscriptionRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/subscribe")
    public ResponseEntity<NotificationSubsResponse> unsubscribeFromTopic(@RequestBody CreateSubscriptionRequest request) {

        return new ResponseEntity<>(new NotificationSubsResponse(notificationService.unsubscribeToTopic(request)), HttpStatus.OK);
    }

    @GetMapping("{title}")
    public ResponseEntity<Page<NotificationResponse>> getNotificationByTopic(@PathVariable("title") String title,
                                                                             @RequestParam(required = false) Integer pageNumber,
                                                                             @RequestParam(required = false) Integer pageSize,
                                                                             @RequestParam(required = false) String sortBy) {

        return new ResponseEntity<>(notificationService.getMessageByTitle(title,pageNumber,pageSize,sortBy).map(NotificationResponse::new),HttpStatus.OK);
    }


}