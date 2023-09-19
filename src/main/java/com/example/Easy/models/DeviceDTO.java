package com.example.Easy.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Builder
public class DeviceDTO {
    private UUID deviceID;
    private String timeZone;
    private DeviceType deviceType;
    private String deviceToken;
    private List<UserDTO> users;


    public void removeUser(UserDTO userDTO){
        users.remove(userDTO);
    }
}
