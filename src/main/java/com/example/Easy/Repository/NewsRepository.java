package com.example.Easy.Repository;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<NewsEntity, UUID> {
    Page<NewsEntity> findByTitle(String title, Pageable pageable);
    //Page<NewsEntity> findByNewsCategoryEntity(NewsCategoryEntity category);
    List<NewsEntity> findByAuthor(UserEntity author);
}
