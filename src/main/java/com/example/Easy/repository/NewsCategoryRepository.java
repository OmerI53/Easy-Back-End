package com.example.Easy.repository;

import com.example.Easy.entities.NewsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NewsCategoryRepository extends JpaRepository<NewsCategoryEntity, Long> {

    List<NewsCategoryEntity> findByparent(NewsCategoryEntity parent);
    NewsCategoryEntity findByname(String name);
}
