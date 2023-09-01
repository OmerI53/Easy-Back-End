package com.example.Easy.Entites;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
public class NotificationEntity {
    @Id
    @NotBlank
    @NotNull
    private UUID notificationID;
    @NotBlank
    @NotNull
    private String userToken;
    @NotBlank
    @NotNull
    private String topic;
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    @NotNull
    private String image;
    @NotBlank
    @NotNull
    private String text;

}
