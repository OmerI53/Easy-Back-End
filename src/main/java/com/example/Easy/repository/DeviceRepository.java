package com.example.Easy.repository;

import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.models.DeviceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID>, JpaSpecificationExecutor<DeviceEntity> {

    Page<DeviceEntity> findByTimeZoneAndDeviceType(String timeZone, DeviceType deviceType, PageRequest pageRequest);
    Page<DeviceEntity> findByTimeZone(String timeZone, PageRequest pageRequest);
    Page<DeviceEntity> findByDeviceType(DeviceType deviceType, PageRequest pageRequest);
}
