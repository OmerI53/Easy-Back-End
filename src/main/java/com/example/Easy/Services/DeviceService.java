package com.example.Easy.Services;

import com.example.Easy.Entities.DeviceEntity;
import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Mappers.DeviceMapper;
import com.example.Easy.Mappers.UserMapper;
import com.example.Easy.Models.AuthResponseDTO;
import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repository.DeviceRepository;
import com.example.Easy.Repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final NotificationService notificationService;

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
    public String addNewDevice(DeviceDTO deviceDTO) throws FirebaseMessagingException {
        //TODO cant bootstrap data since a real FCM is needed
        notificationService.subscribeToTopic("All",deviceDTO.getDeviceToken());
        return deviceRepository.save(deviceMapper.toDeviceEntity(deviceDTO)).getDeviceID().toString();
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
    public AuthResponseDTO loginToDevice(UUID deviceId, UserDTO userDTO) {
        AuthResponseDTO auth = authenticationService.authenticate(userDTO);
        DeviceEntity device = deviceRepository.findById(deviceId).orElse(null);
        UserEntity user = userRepository.findByEmail(userDTO.getEmail());
        if(!device.getUsers().contains(user)){
            device.getUsers().add(user);
            user.getDevice().add(device);
            deviceRepository.save(device);
            userRepository.save(user);
        }
        try {
            notificationService.subscribeToTopic(user.getUserId().toString(),device.getDeviceToken());
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        return auth;
    }
    public void logoutFromDevice(UUID deviceId, UserDTO userDTO) {
        DeviceEntity device = deviceRepository.findById(deviceId).orElse(null);
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElse(null);
        user.getDevice().remove(device);
        device.getUsers().remove(user);
        try {
            notificationService.unsubscribeToTopic(user.getUserId().toString(),device.getDeviceToken());
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        deviceRepository.save(device);
        userRepository.save(user);
    }
    public Page<UserDTO> getDeviceUsers(UUID deviceId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        List<UserDTO> userDTOS = deviceRepository.findById(deviceId).orElse(null).getUsers()
                .stream().map(userMapper::toUserDTO).collect(Collectors.toList());
        return new PageImpl<>(pagedListHolderFromRequest(pageRequest,userDTOS).getPageList());
    }
    private PagedListHolder pagedListHolderFromRequest(PageRequest pageRequest, List list){
        PagedListHolder pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(),true,true));
        return pagedListHolder;
    }


}
