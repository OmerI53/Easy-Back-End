package com.example.Easy.Repository;

import com.example.Easy.Entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<NewsEntity, UUID> {


}
