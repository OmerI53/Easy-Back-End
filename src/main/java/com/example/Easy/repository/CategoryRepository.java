package com.example.Easy.repository;

import com.example.Easy.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByparent(CategoryEntity parent);
    CategoryEntity findByName(String name);
}
