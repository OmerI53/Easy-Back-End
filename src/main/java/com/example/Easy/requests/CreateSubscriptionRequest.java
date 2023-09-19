package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSubscriptionRequest {
    private String topic;
    private String fcmToken;
}
