package com.example.Easy.repository;

import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<NewsEntity, UUID> {
    Page<NewsEntity> findByTitle(String title, Pageable pageable);
    List<NewsEntity> findByAuthor(UserEntity author);
}
