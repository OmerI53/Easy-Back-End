package com.example.Easy.Services;

import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repository.UserRepository;
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
    public String register(UserDTO userDTO) {
        UserEntity userEntity = UserEntity.builder()
                .name(userDTO.getName())
                .userToken(userDTO.getUserToken())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        userRepository.save(userEntity);
        return jwtService.generateToken(userEntity);
    }

    public String authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );
        UserEntity user = userRepository.findByEmail(userDTO.getEmail());
        return jwtService.generateToken(user);
    }
}
