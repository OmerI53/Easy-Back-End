package com.example.Easy.Repositories;

import com.example.Easy.Entites.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    List<NotificationEntity> getNotificationByTitle(String title);
}
