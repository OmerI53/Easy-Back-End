package com.example.Easy.Repository;

import com.example.Easy.Entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

    List<NotificationEntity> getNotificationByTitle(String title);
}
