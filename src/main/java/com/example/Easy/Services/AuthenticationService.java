package com.example.Easy.Services;

import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Models.AuthResponseDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repository.UserRepository;
import com.google.api.Authentication;
import lombok.RequiredArgsConstructor;
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
        UserEntity userEntity = createUserEntityFromDTO(userDTO);
        userRepository.save(userEntity);

        String token = jwtService.generateToken(userEntity);
        return createAuthResponseDTO(userEntity, token);
    }

    public AuthResponseDTO authenticate(UserDTO userDTO) {
        authenticateUser(userDTO.getEmail(), userDTO.getPassword());

        UserEntity user = userRepository.findByEmail(userDTO.getEmail());
        String token = jwtService.generateToken(user);

        return createAuthResponseDTO(user, token);
    }

    private UserEntity createUserEntityFromDTO(UserDTO userDTO) {
        return UserEntity.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .image(userDTO.getImage())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }

    private AuthResponseDTO createAuthResponseDTO(UserEntity user, String token) {
        return AuthResponseDTO.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .image(user.getImage())
                .email(user.getEmail())
                .jwt(token)
                .build();
    }
}

