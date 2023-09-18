package com.example.Easy.repository;

import com.example.Easy.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {

}

