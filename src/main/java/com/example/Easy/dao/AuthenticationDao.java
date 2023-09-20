package com.example.Easy.dao;

import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.AuthResponseDTO;
import com.example.Easy.models.UserDTO;

public interface AuthenticationDao {
    public AuthResponseDTO register(UserDTO userDTO);
    public AuthResponseDTO authenticate(UserDTO userDTO);
}
