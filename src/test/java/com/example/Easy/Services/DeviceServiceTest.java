package com.example.Easy.Services;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Models.DeviceType;
import com.example.Easy.Repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DeviceServiceTest {

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    DeviceService deviceService;

    @Test
    void bootstrap(){
        DeviceEntity deviceEntity1 = DeviceEntity.builder()
                .timeZone("GMT+03:00")
                .deviceType(DeviceType.IOS)
                .deviceToken("eYsHNOnxQ3K83vJB5vCkwQ:APA91bFp2N_MRa3penVKoSfFSCQl1er3FSZtfhTFKP-NcRWrtMIxhumE9Dp5NE2MoU2A-7Z-8XG_6LQ4r4GgPe3Nl7Q-byJBe92xbLZ3m_BGEMZb2xC_BkyDA63D_yzdqnd7RPxfJbZc")
                .build();

        DeviceEntity deviceEntity2 = DeviceEntity.builder()
                .timeZone("GMT+04:00")
                .deviceType(DeviceType.ANDROID)
                .deviceToken("da37GPpPQR2t325Jg8sR-v:APA91bGcvhE_J3gX2udxEPJWgeR_M5e4y_jXpWlNozJnann2493qPn_1mewKchPvigMofwSLBH5PAr-msEL8DZbvURberbuA6667jsk-53JPvPlK5ugbvkSlP0CBADXW8xOLAbFq5lZa")
                .build();

        DeviceEntity deviceEntity3 = DeviceEntity.builder()
                .timeZone("GMT+04:00")
                .deviceType(DeviceType.MACOS)
                .deviceToken("dGmSJfHQTxuXBNcMRz9Pv9:APA91bGtL-HlO088BAetJTkVc6TWQk2VpMNdNTspqvnwRziRJN-3oLIupE_ik6c8uO_b8kKP_56FibcLVdaKbcDQFV_UZ8x-tyVR9m2fMW_ZiCk3nblsX_TT7ZksxRSDPqmzg-UJRpje")
                .build();

        DeviceEntity deviceEntity4 = DeviceEntity.builder()
                .timeZone("GMT+05:00")
                .deviceType(DeviceType.WEB)
                .deviceToken("fgiMSYssSZu_i4DjI3wxY5:APA91bH5Ylp92cYdZVBHoEqozkbBQ1-EGQI2JMTdmReLeMUFPqNsq1T8OIvBDasBGZjI_aFTeEhQU2lAtqGKVjong8YIUvBckiHvn-GjgVbvF9I0Ih1y316xtARCr_Ys50w8HE-Fy5nC")
                .build();

        deviceRepository.save(deviceEntity1);
        deviceRepository.save(deviceEntity2);
        deviceRepository.save(deviceEntity3);
        deviceRepository.save(deviceEntity4);

    }

    @Test
    void  addNewDeviceTest(){
        DeviceEntity deviceEntity = DeviceEntity.builder()
                .timeZone("GMT+03:00")
                .deviceType(DeviceType.ANDROID)
                .deviceToken("eYsHNOnxQ3K83vJB5vCkwQ:APA91bFp2N_MRa3penVKoSfFSCQl1er3FSZtfhTFKP-NcRWrtMIxhumE9Dp5NE2MoU2A-7Z-8XG_6LQ4r4GgPe3Nl7Q-byJBe92xbLZ3m_BGEMZb2xC_BkyDA63D_yzdqnd7RPxfJbZc")
                .build();
        deviceRepository.save(deviceEntity);
    }
    @Test
    void getAlldevice(){
        List<DeviceEntity> deviceEntities =deviceRepository.findAll();
        System.out.println(deviceEntities);
    }

    @Test
    void patchTest(){
        UUID userId =UUID.fromString("560dec85-8ecc-4a1b-9c2a-061924335b76");
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .deviceToken("10")
                .build();
        deviceService.patchDevice(userId,deviceDTO);
    }
}