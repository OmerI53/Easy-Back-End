package com.example.Easy.Repositories;

import com.example.Easy.Entities.NewsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCategoryRepository extends JpaRepository<NewsCategoryEntity, Long> {
    NewsCategoryEntity getByName(String name);
}
