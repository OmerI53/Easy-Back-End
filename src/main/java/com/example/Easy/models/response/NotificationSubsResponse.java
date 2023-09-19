package com.example.Easy.models.response;

import com.example.Easy.requests.CreateSubscriptionRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationSubsResponse {
    private String topic;
    private String fcmToken;

    public NotificationSubsResponse(CreateSubscriptionRequest request) {
        this.topic = request.getTopic();
        this.fcmToken=request.getFcmToken();
    }


}
