package com.example.Easy.Controllers;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Services.DeviceService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;


    @PostMapping("/new")
    public ResponseEntity addNewDevice(@RequestBody DeviceDTO deviceDTO) throws FirebaseMessagingException {
        //TODO cant bootstrap data since a real FCM is needed
        deviceService.addNewDevice(deviceDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
