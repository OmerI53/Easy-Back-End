package com.example.Easy.Controllers;

import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Services.DeviceService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;


    @PostMapping("/new")
    public DeviceDTO addNewDevice(@RequestBody DeviceDTO deviceDTO) throws FirebaseMessagingException {
        //TODO cant bootstrap data since a real FCM is needed
        return deviceService.addNewDevice(deviceDTO);
    }

    @DeleteMapping("{deviceId}")
    public ResponseEntity removeDeviceById(@PathVariable("deviceId")UUID deviceId){
        deviceService.removeDeviceById(deviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public Page<DeviceDTO> listAllDevices(@RequestParam(required = false) Integer pageNumber,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String sortBy){
        return deviceService.listAllDevices(pageNumber, pageSize, sortBy);
    }

    @PatchMapping("{deviceId}")
    public ResponseEntity patchDevice(@PathVariable("deviceId") UUID deviceId,@RequestBody DeviceDTO deviceDTO){
        deviceService.patchDevice(deviceId,deviceDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login/{deviceId}")
    public ResponseEntity loginToDevice(@PathVariable("deviceId") UUID deviceId, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(deviceService.loginToDevice(deviceId,userDTO));
    }
    @DeleteMapping("logout/{deviceId}")
    public ResponseEntity logoutFromDevice(@PathVariable("deviceId") UUID deviceId, @RequestBody UserDTO userDTO){
        deviceService.logoutFromDevice(deviceId,userDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
    @GetMapping("/users/{deviceId}")
    public Page<UserDTO> getDeviceUsers(@PathVariable("deviceId") UUID deviceId,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String sortBy){
        return deviceService.getDeviceUsers(deviceId,pageNumber,pageSize,sortBy);
    }

}
