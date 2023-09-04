package com.example.Easy.Controllers;

import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    public ResponseEntity AddNewDevice(@RequestBody DeviceDTO deviceDTO){
        return null;
    }
}
