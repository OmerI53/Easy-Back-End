package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateNotificationRequest {
    private String title;
    private String text;
    private String topic;
}
