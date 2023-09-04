package com.example.Easy.Entites;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import javax.annotation.Nullable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "Notifications")
public class NotificationEntity {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "notificationId", length = 36, columnDefinition = "varchar(36)", updatable = false,nullable = false)
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

    @Nullable
    private String image;

    @NotBlank
    @NotNull
    private String text;

}
