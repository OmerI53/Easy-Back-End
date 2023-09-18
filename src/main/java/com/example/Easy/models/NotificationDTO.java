package com.example.Easy.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NotificationDTO {
    private UUID notificationID;
    private String userToken;
    private String topic;
    private String title;
    private String image;
    private String text;
}

