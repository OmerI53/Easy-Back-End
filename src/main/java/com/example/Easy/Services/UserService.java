package com.example.Easy.Services;

import com.example.Easy.Entites.UserEntity;
import com.example.Easy.Mappers.UserMapper;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repositories.UserRepository;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public void AddNewUser(UserDTO userDTO){
        userRepository.save(userMapper.toUserEntity(userDTO));
    }

    public void DeleteUser(UUID uuid){
        userRepository.deleteById(uuid);
    }
    public void DeleteAllUsers(){
        userRepository.deleteAll();
    }
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }
    public UserDTO getUserByID(UUID id){

        return userMapper.toUserDTO(userRepository.findById(id).orElse(null));
    }

}
