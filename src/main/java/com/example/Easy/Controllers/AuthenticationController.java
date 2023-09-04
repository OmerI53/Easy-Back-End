package com.example.Easy.Controllers;

import com.example.Easy.Models.UserDTO;
import com.example.Easy.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authenticationService.register(userDTO));
    }
    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authenticationService.authenticate(userDTO));
    }
}
