package com.example.Easy.Mappers;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Models.DeviceDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-29T17:32:52+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class DeviceMapperImpl implements DeviceMapper {

    @Override
    public DeviceEntity toDeviceEntity(DeviceDTO deviceDTO) {
        if ( deviceDTO == null ) {
            return null;
        }

        DeviceEntity.DeviceEntityBuilder deviceEntity = DeviceEntity.builder();

        deviceEntity.deviceID( deviceDTO.getDeviceID() );
        deviceEntity.timeZone( deviceDTO.getTimeZone() );
        deviceEntity.deviceType( deviceDTO.getDeviceType() );
        deviceEntity.deviceToken( deviceDTO.getDeviceToken() );

        return deviceEntity.build();
    }

    @Override
    public DeviceDTO toDeviceDTO(DeviceEntity deviceEntity) {
        if ( deviceEntity == null ) {
            return null;
        }

        DeviceDTO.DeviceDTOBuilder deviceDTO = DeviceDTO.builder();

        deviceDTO.deviceID( deviceEntity.getDeviceID() );
        deviceDTO.timeZone( deviceEntity.getTimeZone() );
        deviceDTO.deviceType( deviceEntity.getDeviceType() );
        deviceDTO.deviceToken( deviceEntity.getDeviceToken() );

        return deviceDTO.build();
    }
}
