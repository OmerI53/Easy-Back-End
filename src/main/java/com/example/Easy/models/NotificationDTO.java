package com.example.Easy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private UUID notificationID;
    private String userToken;
    private String topic;
    private String title;
    private String image;
    private String text;
}

