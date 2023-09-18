package com.example.Easy.controllers;

import com.example.Easy.models.AuthResponseDTO;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.services.DeviceService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/add")
    public DeviceDTO add(@RequestBody DeviceDTO deviceDTO) throws FirebaseMessagingException {
        //TODO cant bootstrap data since a real FCM is needed
        return deviceService.add(deviceDTO);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> remove(@PathVariable("deviceId")UUID deviceId){
        deviceService.delete(deviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/get-all")
    public Page<DeviceDTO> get(@RequestParam(required = false) Integer pageNumber,
                               @RequestParam(required = false) Integer pageSize,
                               @RequestParam(required = false) String sortBy){
        return deviceService.listAllDevices(pageNumber, pageSize, sortBy);
    }

    @PatchMapping("/update/{deviceId}")
    public ResponseEntity<Void> patch(@PathVariable("deviceId") UUID deviceId, @RequestBody DeviceDTO deviceDTO){
        deviceService.patch(deviceId,deviceDTO);
        return new ResponseEntity<>(OK);
    }


    @PostMapping("/login/{deviceId}")
    public ResponseEntity<AuthResponseDTO> login(@PathVariable("deviceId") UUID deviceId, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(deviceService.login(deviceId,userDTO));
    }
    @DeleteMapping("/logout/{deviceId}")
    public ResponseEntity<Void> logout(@PathVariable("deviceId") UUID deviceId, @RequestBody UserDTO userDTO){
        deviceService.logout(deviceId,userDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @GetMapping("/{deviceId}")
    public Page<UserDTO> getDeviceUsers(@PathVariable("deviceId") UUID deviceId,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String sortBy){
        return deviceService.get(deviceId,pageNumber,pageSize,sortBy);
    }

}
