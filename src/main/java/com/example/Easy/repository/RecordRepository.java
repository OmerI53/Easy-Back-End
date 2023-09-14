package com.example.Easy.repository;

import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<RecordsEntity, UUID> {
    RecordsEntity findByUserAndNews(UserEntity user, NewsEntity news);
    List<RecordsEntity> findByUser(UserEntity user);
}
