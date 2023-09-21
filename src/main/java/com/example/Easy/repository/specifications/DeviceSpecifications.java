package com.example.Easy.repository.specifications;

import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.models.DeviceType;
import org.springframework.data.jpa.domain.Specification;

public class DeviceSpecifications {
    public static Specification<DeviceEntity> getSpecifiedDevices(String timeZone, DeviceType deviceType){
        return (root,query,builder)->Specification.where(withTimeZone(timeZone))
                .and(withDeviceType(deviceType))
                .toPredicate(root,query,builder);
    }

    public static Specification<DeviceEntity> withTimeZone(String timeZone){
        return (root,query,builder)->{
            if(timeZone==null)
                return null;
            return builder.equal(root.get("timeZone"),timeZone);
        };
    }
    public static Specification<DeviceEntity> withDeviceType(DeviceType deviceType){
        return (root,query,builder)->{
            if(deviceType==null)
                return null;
            return builder.equal(root.get("deviceType"),deviceType);
        };
    }
}
