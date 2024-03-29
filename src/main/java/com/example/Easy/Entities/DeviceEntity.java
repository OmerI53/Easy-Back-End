package com.example.Easy.Entities;

import com.example.Easy.Models.DeviceType;
import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Devices")
public class DeviceEntity {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "deviceId",length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID deviceID;

    @NotNull
    @NotBlank
    private String timeZone;

    @Column(columnDefinition = "enum('IOS','ANDROID')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private DeviceType deviceType;

    @NotNull
    @NotBlank
    private String deviceToken;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "deviceUser",columnDefinition = "varchar(36)")
    private UUID userId;

}
