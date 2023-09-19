package com.example.Easy.services;

import com.example.Easy.models.AuthenticatedUserDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.requests.LoginRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Transactional
    public AuthenticatedUserDTO register(UserDTO user) {
        //TODO cant since a real FCM token is needed
        //notificationService.subscribeToTopic("All",userDTO.getUserToken());
        String token = jwtService.generateToken(user);
        return AuthenticatedUserDTO.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .image(user.getImage())
                .email(user.getEmail())
                .jwt(token)
                .build();
    }

    public String generateJTW(UserDTO user) {
        return jwtService.generateToken(user);
    }

    public AuthenticatedUserDTO authenticate(LoginRequest loginRequest, UserDTO user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        String token = jwtService.generateToken(user);
        return AuthenticatedUserDTO.builder()
                .name(user.getName())
                .jwt(token)
                .image(user.getImage())
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
    }
}
