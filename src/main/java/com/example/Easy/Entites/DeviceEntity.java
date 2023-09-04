package com.example.Easy.Entites;

import com.example.Easy.Models.DeviceType;
import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;
@Entity
@Getter
@Setter
@Builder
public class DeviceEntity {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "deviceId", columnDefinition = "varchar(36)", length = 36, updatable = false, nullable = false)
    private UUID deviceID;

    @NotNull
    @NotBlank
    private String timeZone;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "enum('IOS', 'ANDROID')")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @NotNull
    @NotBlank
    private String deviceToken;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "deviceUser", columnDefinition = "varchar(36)")
    private UUID userID;

}
