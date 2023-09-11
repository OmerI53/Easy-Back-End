package com.example.Easy.Repository;

import com.example.Easy.Entities.NewsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NewsCategoryRepository extends JpaRepository<NewsCategoryEntity, Long> {

    List<NewsCategoryEntity> findByparent(NewsCategoryEntity parent);
    NewsCategoryEntity findByname(String name);
}
