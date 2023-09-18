package com.example.Easy.entities;

import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.database.annotations.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Notifications")
public class NotificationEntity {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "notificationId", length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID notificationID;

    private String userToken;

    private String topic;

    @NotNull
    private String title;

    @Nullable
    private String image;


    @NotNull
    @NotBlank
    private String text;
}

