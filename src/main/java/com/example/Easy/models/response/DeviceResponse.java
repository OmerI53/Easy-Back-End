package com.example.Easy.models.response;

import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.DeviceType;
import com.example.Easy.models.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class DeviceResponse extends BaseResponse{
    private UUID deviceId;
    private String timeZone;
    private DeviceType deviceType;
    private List<String> users;

    public DeviceResponse(DeviceDTO deviceDTO) {
        this.deviceId = deviceDTO.getDeviceID();
        this.timeZone = deviceDTO.getTimeZone();
        this.deviceType = deviceDTO.getDeviceType();
        this.users = getDTOUsers(deviceDTO.getUsers());
    }
    private List<String> getDTOUsers(List<UserDTO> users){
        if (users==null)
            return null;
        return users
                .stream().map(UserDTO::getName)
                .collect(Collectors.toList());
    }
}
