package com.example.Easy.models.response;

import com.example.Easy.models.NotificationDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
    private String text;
    private String title;
    public NotificationResponse(NotificationDTO notificationDTO) {
        this.text=notificationDTO.getText();
        this.title= notificationDTO.getTitle();
    }
}
