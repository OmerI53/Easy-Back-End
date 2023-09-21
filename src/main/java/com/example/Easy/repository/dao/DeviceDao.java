package com.example.Easy.repository.dao;

import com.example.Easy.mappers.DeviceMapper;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.DeviceType;
import com.example.Easy.repository.DeviceRepository;
import com.example.Easy.repository.specifications.DeviceSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DeviceDao implements Dao<DeviceDTO>{

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final ResourceBundleMessageSource source;

    @Override
    public DeviceDTO get(UUID id) {
        return deviceMapper.toDeviceDTO(deviceRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(source.getMessage("device.notfound", null, LocaleContextHolder.getLocale()))));
    }

    @Override
    public List<DeviceDTO> getAll() {
        return deviceRepository.findAll()
                .stream().map(deviceMapper::toDeviceDTO)
                .collect(Collectors.toList());
    }
    public Page<DeviceDTO> getAll(String timeZone, DeviceType deviceType, PageRequest pageRequest) {
        return deviceRepository.findAll(DeviceSpecifications.getSpecifiedDevices(timeZone,deviceType),pageRequest)
                .map(deviceMapper::toDeviceDTO);
    }

    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        return deviceMapper.toDeviceDTO(deviceRepository.save(deviceMapper.toDeviceEntity(deviceDTO)));
    }

    @Override
    public DeviceDTO update(DeviceDTO deviceDTO) {
        return deviceMapper.toDeviceDTO(deviceRepository.save(deviceMapper.toDeviceEntity(deviceDTO)));
    }

    @Override
    public DeviceDTO delete(DeviceDTO deviceDTO) {
         deviceRepository.delete(deviceMapper.toDeviceEntity(deviceDTO));
         return deviceDTO;
    }


}
