package com.example.Easy.mappers;

import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper
public interface DeviceMapper {

    DeviceEntity toDeviceEntity(DeviceDTO deviceDTO);
    @Named("toDeviceDTO")
    @Mappings(value = {
            @Mapping(target ="users",qualifiedByName = "userMapper")

    })
    DeviceDTO toDeviceDTO(DeviceEntity deviceEntity);

    @Named("userMapper")
    @Mappings(value = {
            @Mapping(target = "news",ignore = true),
            @Mapping(target = "comments",ignore = true),
            @Mapping(target = "following",ignore = true),
            @Mapping(target = "followers",ignore = true),
            @Mapping(target = "userRecords",ignore = true),
            @Mapping(target = "email",ignore = true),
            @Mapping(target = "password",ignore = true),
            @Mapping(target="devices",ignore = true),
    })
    UserDTO userMapper(UserEntity userEntity);


}
