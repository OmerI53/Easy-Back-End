package com.example.Easy.Repository;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.RecordsEntity;
import com.example.Easy.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecordsRepository extends JpaRepository<RecordsEntity, UUID> {
    Page<RecordsEntity> findByUser(UserEntity user, Pageable pageable);
    RecordsEntity findByUserAndNews(UserEntity user,NewsEntity news);
    List<RecordsEntity> findByNewsAndPostlike(NewsEntity news,boolean postlike);
    List<RecordsEntity> findByNewsAndPostbookmark(NewsEntity news,boolean postbookmark);
    List<RecordsEntity> findByUserAndPostlike(UserEntity user, boolean postlike);
    List<RecordsEntity> findByUserAndPostbookmark(UserEntity user,boolean postbookmark);
}
