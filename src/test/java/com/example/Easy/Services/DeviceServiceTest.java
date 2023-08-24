package com.example.Easy.Services;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Models.DeviceType;
import com.example.Easy.Repository.DeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DeviceServiceTest {

    @Autowired
    DeviceRepository deviceRepository;

    @Test
    void  addNewDeviceTest(){
        String token = "eYsHNOnxQ3K83vJB5vCkwQ:APA91bFp2N_MRa3penVKoSfFSCQl1er3FSZtfhTFKP-NcRWrtMIxhumE9Dp5NE2MoU2A-7Z-8XG_6LQ4r4GgPe3Nl7Q-byJBe92xbLZ3m_BGEMZb2xC_BkyDA63D_yzdqnd7RPxfJbZc";
        System.out.println(token.length());
        DeviceEntity deviceEntity = DeviceEntity.builder()
                .timeZone("GMT+03:00")
                .deviceType(DeviceType.ANDROID)
                .deviceToken("eYsHNOnxQ3K83vJB5vCkwQ:APA91bFp2N_MRa3penVKoSfFSCQl1er3FSZtfhTFKP-NcRWrtMIxhumE9Dp5NE2MoU2A-7Z-8XG_6LQ4r4GgPe3Nl7Q-byJBe92xbLZ3m_BGEMZb2xC_BkyDA63D_yzdqnd7RPxfJbZc")
                .build();
        deviceRepository.save(deviceEntity);
    }
    @Test
    void deleteDeviceTest() {
        String token = "e_5VwVHYRJG8J3GrGdxmol:APA91bG09ABYw_kKPPBZUCCpZddOPzB2b9Edyhl562UCSqwd4uLSFmFMoDwiE0fyobpZ4TkCGw-dbIs_dyHf3rp5Gu05hCN2X-_y18GTWoR0_ZKnuIvWlh3dmIUnYqRiJEmKOf8ar7oU";
        System.out.println(token.length());

        DeviceEntity deviceToDelete = DeviceEntity.builder()
                .deviceId(UUID.fromString("88fd8c46-e469-4a86-9e66-c1a1f33d58d5"))
                .build();

        deviceRepository.delete(deviceToDelete);

        assertFalse(deviceRepository.existsById(deviceToDelete.getDeviceId()));
    }
    @Test
    void ChangeDevice_token() {
        String token = "e_5VwVHYRJG8J3GrGdxmol:APA91bG09ABYw_kKPPBZUCCpZddOPzB2b9Edyhl562UCSqwd4uLSFmFMoDwiE0fyobpZ4TkCGw-dbIs_dyHf3rp5Gu05hCN2X-_y18GTWoR0_ZKnuIvWlh3dmIUnYqRiJEmKOf8ar7oU";
        System.out.println(token.length());

        DeviceEntity deviceToDelete = DeviceEntity.builder()
                .deviceId(UUID.fromString("88fd8c46-e469-4a86-9e66-c1a1f33d58d5"))
                .build();

        deviceRepository.delete(deviceToDelete);

        assertFalse(deviceRepository.existsById(deviceToDelete.getDeviceId()));
    }


}