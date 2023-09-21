package com.example.Easy.repository.dao;

import com.example.Easy.mappers.UserMapperImpl;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserDao implements Dao<UserDTO> {

    private final UserRepository userRepository;
    private final UserMapperImpl userMapper;
    private final ResourceBundleMessageSource source;

    @Override
    public UserDTO get(UUID id) {
        return userMapper.toUserDTO(userRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(source.getMessage("user.notfound", null, LocaleContextHolder.getLocale()))));

    }

    public UserDTO get(String name) {
        return userMapper.toUserDTO(userRepository.findByName(name));
    }

    public UserDTO getByEmail(String email) {
        return userMapper.toUserDTO(userRepository.findByEmail(email));
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream().map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public Page<UserDTO> getAll(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).map(userMapper::toUserDTO);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return userMapper.toUserDTO(userRepository.save(userMapper.toUserEntity(userDTO)));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        return userMapper.toUserDTO(userRepository.save(userMapper.toUserEntity(userDTO)));
    }

    @Override
    public UserDTO delete(UserDTO userDTO) {
        userRepository.delete(userMapper.toUserEntity(userDTO));
        return userDTO;
    }


}
