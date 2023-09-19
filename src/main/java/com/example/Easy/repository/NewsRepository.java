package com.example.Easy.repository;

import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<NewsEntity, UUID> {
    Page<NewsEntity> findByTitle(String title, Pageable pageable);

    @Query(value="select * from news a where a.category_category_id= :categoryId",
            nativeQuery=true)
    List<NewsEntity> findByCategoryid(@Param("categoryId") Long categoryId);

    @Query(value="select * from news a where a.title= :title and a.category_category_id= :categoryId",
            nativeQuery=true)
    List<NewsEntity> findByTitleAndCategoryid(@Param("title") String title,@Param("categoryId") Long categoryId);
    List<NewsEntity> findByAuthor(UserEntity author);
}
