package com.example.Easy.Services;

import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Models.AuthResponseDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponseDTO register(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .image(userDTO.getImage())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        try{
            userRepository.save(userEntity);
        }catch (DataIntegrityViolationException e) {
            throw new NullPointerException("duplicate email");
        }

        //TODO cant since a real FCM token is needed
        //notificationService.subscribeToTopic("All",userDTO.getUserToken());
        String token = jwtService.generateToken(userEntity);
        return AuthResponseDTO.builder()
                .name(userEntity.getName())
                .userId(userEntity.getUserId())
                .image(userEntity.getImage())
                .email(userEntity.getEmail())
                .jwt(token)
                .build();
    }

    public AuthResponseDTO authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );
        UserEntity user = userRepository.findByEmail(userDTO.getEmail());
        String token = jwtService.generateToken(user);
        return AuthResponseDTO.builder()
                .name(user.getName())
                .jwt(token)
                .image(user.getImage())
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
    }
}
