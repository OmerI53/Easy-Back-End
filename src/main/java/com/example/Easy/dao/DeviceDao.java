package com.example.Easy.dao;

import com.example.Easy.models.AuthResponseDTO;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.UserDTO;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface DeviceDao {
    public DeviceDTO add(DeviceDTO deviceDTO) throws FirebaseMessagingException;
    public void delete(UUID deviceId);
    public Page<DeviceDTO> listAllDevices(Integer pageNumber, Integer pageSize, String sortBy);
    public void patch(UUID deviceId, DeviceDTO deviceDTO);
    public AuthResponseDTO login(UUID deviceId, UserDTO userDTO);
    public void logout(UUID deviceId, UserDTO userDTO);
    public Page<UserDTO> get(UUID deviceId, Integer pageNumber, Integer pageSize, String sortBy);

}
