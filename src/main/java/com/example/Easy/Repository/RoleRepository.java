package com.example.Easy.Repository;

import com.example.Easy.Entities.RoleEntity;
import com.example.Easy.Models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleType name);
}
