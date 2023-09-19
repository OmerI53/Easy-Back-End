package com.example.Easy.repository;

import com.example.Easy.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByEmail(String userEmail);
    Page<UserEntity> findByName(String name, PageRequest pageRequest);
}
