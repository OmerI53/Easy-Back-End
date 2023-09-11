package com.example.Easy.Repository;

import com.example.Easy.Entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {

}
