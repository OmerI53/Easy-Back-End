package com.example.Easy.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
public class NotificationDTO {

    private String userToken;
    private String title;
    private String body;
    private String image;
    private Map<String, String> data;


}
