package com.example.Easy.controllers;

import com.example.Easy.models.DeviceType;
import com.example.Easy.models.response.DeviceResponse;
import com.example.Easy.models.response.LoginResponse;
import com.example.Easy.requests.CreateDeviceRequest;
import com.example.Easy.requests.LoginRequest;
import com.example.Easy.requests.LogoutRequest;
import com.example.Easy.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final ResourceBundleMessageSource source;

    @GetMapping
    public ResponseEntity<Page<DeviceResponse>> listAllDevices(@RequestParam(required = false) Integer pageNumber,
                                                               @RequestParam(required = false) Integer pageSize,
                                                               @RequestParam(required = false) String sortBy,
                                                               @RequestParam(required = false) String timeZone,
                                                               @RequestParam(required = false) DeviceType deviceType) {
        return new ResponseEntity<>(deviceService.listAllDevices(pageNumber, pageSize, sortBy, timeZone, deviceType).map(DeviceResponse::new), HttpStatus.OK);
    }
    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable("deviceId") UUID deviceId) {

        return new ResponseEntity<DeviceResponse>(new DeviceResponse(deviceService.getDevice(deviceId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<DeviceResponse> addDevice(@RequestBody CreateDeviceRequest createDeviceRequest) {
        try {
            return new ResponseEntity<>(new DeviceResponse(deviceService.addDevice(createDeviceRequest)), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new NullPointerException(source.getMessage("device.duplicate.token", null, LocaleContextHolder.getLocale()));
        }
    }
    @PatchMapping("/{deviceId}")
    public ResponseEntity<DeviceResponse> patchDevice(@PathVariable("deviceId") UUID deviceId
            , @RequestBody CreateDeviceRequest createDeviceRequest) {
        return new ResponseEntity<>(new DeviceResponse(deviceService.patchDevice(deviceId, createDeviceRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<DeviceResponse> deleteDevice(@PathVariable("deviceId") UUID deviceId) {
        return new ResponseEntity<>(new DeviceResponse(deviceService.removeDeviceById(deviceId)), HttpStatus.OK);
    }

    //Ask below
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginToDevice(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<LoginResponse>(new LoginResponse(deviceService.loginToDevice(loginRequest)), HttpStatus.OK);
    }
    @DeleteMapping("/logout")
    public ResponseEntity logoutFromDevice(@RequestBody LogoutRequest logoutRequest) {
        return new ResponseEntity<>(LocalDateTime.now(), HttpStatus.NO_CONTENT);
    }
}
