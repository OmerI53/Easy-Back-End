package com.example.Easy.Repositories;

import com.example.Easy.Entites.DeviceEntity;
import com.example.Easy.Entites.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {

}
