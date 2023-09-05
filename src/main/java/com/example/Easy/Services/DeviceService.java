package com.example.Easy.Services;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Mappers.DeviceMapper;
import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Repository.DeviceRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final NotificationService notificationService;
    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final static int DEFAULT_PAGE=0;
    private final static int DEFAULT_PAGE_SIZE=25;
    private final static String DEFAULT_SORT="timeZone";
    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String sortBy){
        // if not initilized set it to default

        int queryPageNumber;
        int queryPageSize;
        String querySortBy;

        if(pageNumber!=null && pageNumber>0)
            queryPageNumber = pageNumber-1; //it is 0 indexed, for first page, number is 1.
        else
            queryPageNumber=DEFAULT_PAGE;

        if(pageSize==null)
            queryPageSize=DEFAULT_PAGE_SIZE;
        else
            queryPageSize=pageSize;
        //setting a max size
        if(queryPageSize>100)
            queryPageSize=100;

        if(sortBy!=null && !sortBy.equals(""))
            querySortBy=sortBy;
        else
            querySortBy=DEFAULT_SORT;

        Sort sort = Sort.by(Sort.Order.desc(querySortBy));
        return PageRequest.of(queryPageNumber,queryPageSize,sort);

    }
    public DeviceDTO addNewDevice(DeviceDTO deviceDTO) throws FirebaseMessagingException {
        //TODO cant bootstrap data since a real FCM is needed
        notificationService.subscribeToTopic("All",deviceDTO.getDeviceToken());
        DeviceEntity device = deviceRepository.save(deviceMapper.toDeviceEntity(deviceDTO));
        return deviceMapper.toDeviceDTO(device);
    }
    public void removeDeviceById(UUID deviceId) {
        deviceRepository.deleteById(deviceId);
    }
    public Page<DeviceDTO> listAllDevices(Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        return deviceRepository.findAll(pageRequest).map(deviceMapper::toDeviceDTO);
    }

    public void patchDevice(UUID deviceId, DeviceDTO deviceDTO) {
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId).orElse(null);
        if(deviceEntity==null)
                return;
        if(deviceDTO.getDeviceToken()!=null & !Objects.equals(deviceDTO.getDeviceToken(), ""))
            deviceEntity.setDeviceToken(deviceDTO.getDeviceToken());
        if(deviceDTO.getTimeZone()!=null & !Objects.equals(deviceDTO.getDeviceToken(), ""))
            deviceEntity.setTimeZone(deviceDTO.getTimeZone());
        if(deviceDTO.getDeviceType()!=null)
            deviceEntity.setDeviceType(deviceDTO.getDeviceType());

        deviceRepository.save(deviceEntity);
    }
}
