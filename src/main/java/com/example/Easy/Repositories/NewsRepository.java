package com.example.Easy.Repositories;

import com.example.Easy.Entites.NewsEntity;
import com.example.Easy.Entites.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<NewsEntity, UUID> {
    List<NewsEntity> findByTitle(String title);

}
