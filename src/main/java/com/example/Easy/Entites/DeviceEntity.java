package com.example.Easy.Entites;

import com.example.Easy.Models.DeviceType;
import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Getter
@Setter
@Builder
public class DeviceEntity {
    @Id
    @NotNull
    @NotBlank
    private UUID deviceID;
    @NotNull
    @NotBlank
    private String timeZone;
    @NotNull
    @NotBlank
    private DeviceType deviceType;
    @NotNull
    @NotBlank
    private String deviceToken;
}
