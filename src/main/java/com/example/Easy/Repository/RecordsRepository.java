package com.example.Easy.Repository;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.RecordsEntity;
import com.example.Easy.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecordsRepository extends JpaRepository<RecordsEntity, UUID> {
    RecordsEntity findByUserAndNews(UserEntity user,NewsEntity news);
    Page<RecordsEntity> findByUser(UserEntity user, Pageable pageable);
}
