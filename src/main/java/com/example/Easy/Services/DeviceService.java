package com.example.Easy.Services;

import com.example.Easy.Controllers.DeviceController;
import com.example.Easy.Mappers.DeviceMapper;
import com.example.Easy.Models.DeviceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private DeviceMapper deviceMapper;
    private DeviceController deviceController;

}
