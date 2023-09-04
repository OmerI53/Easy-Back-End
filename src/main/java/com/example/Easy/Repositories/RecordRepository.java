package com.example.Easy.Repositories;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.RecordsEntity;
import com.example.Easy.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<RecordsEntity, UUID> {
    RecordsEntity findByUserAndNews(UserEntity user, NewsEntity news);
    List<RecordsEntity> findByUser(UserEntity user);
}
