package com.example.Easy.services;

import com.example.Easy.entities.DeviceEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.mappers.DeviceMapper;
import com.example.Easy.mappers.UserMapper;
import com.example.Easy.models.AuthResponseDTO;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.DeviceRepository;
import com.example.Easy.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
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
    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String sortBy) {
        pageNumber = pageNumber != null && pageNumber > 0 ? pageNumber - 1 : DEFAULT_PAGE;
        pageSize = pageSize != null && pageSize > 0 && pageSize <= 100 ? pageSize : DEFAULT_PAGE_SIZE;
        sortBy = sortBy != null && !sortBy.isEmpty() ? sortBy : DEFAULT_SORT;

        Sort sort = Sort.by(Sort.Order.desc(sortBy));
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public DeviceDTO add(DeviceDTO deviceDTO) throws FirebaseMessagingException {
        //TODO cant bootstrap data since a real FCM is needed
        notificationService.subscribeToTopic("All",deviceDTO.getDeviceToken());
        DeviceEntity device = deviceMapper.toDeviceEntity(deviceDTO);
        deviceRepository.save(device);
        return deviceMapper.toDeviceDTO(device);
    }
    public void delete(UUID deviceId) {
        deviceRepository.deleteById(deviceId);
    }
    public Page<DeviceDTO> listAllDevices(Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        return deviceRepository.findAll(pageRequest).map(deviceMapper::toDeviceDTO);
    }

    public void patch(UUID deviceId, DeviceDTO deviceDTO) {
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("deviceId not found!"));
        if(deviceEntity==null || deviceDTO == null)
            return;
        if(deviceDTO.getDeviceToken()!=null & !Objects.equals(deviceDTO.getDeviceToken(), ""))
            deviceEntity.setDeviceToken(deviceDTO.getDeviceToken());
        if(deviceDTO.getTimeZone()!=null & !Objects.equals(deviceDTO.getDeviceToken(), ""))
            deviceEntity.setTimeZone(deviceDTO.getTimeZone());
        if(deviceDTO.getDeviceType()!=null)
            deviceEntity.setDeviceType(deviceDTO.getDeviceType());

        deviceRepository.save(deviceEntity);
    }

    public AuthResponseDTO login(UUID deviceId, UserDTO userDTO) {
        AuthResponseDTO auth = authenticationService.authenticate(userDTO);
        DeviceEntity device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("deviceId not found!"));
        UserEntity user = userRepository.findByEmail(userDTO.getEmail());
        if(device !=null && user !=null && !device.getUsers().contains(user)){
            device.getUsers().add(user);
            user.getDevice().add(device);
            deviceRepository.save(device);
            userRepository.save(user);
        }
        try {
            assert device != null;
            notificationService.subscribeToTopic(userDTO.getUserId().toString(),device.getDeviceToken());
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        return auth;
    }

    public void logout(UUID deviceId, UserDTO userDTO) {
        DeviceEntity device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("deviceId not found!"));
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElseThrow(() -> new RuntimeException("userId not found!"));

        if(device == null || user == null){
            System.out.println("device or user null");
            return;
        }else{
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

    }

    public Page<UserDTO> get(UUID deviceId, Integer pageNumber, Integer pageSize, String sortBy) {
        DeviceEntity device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("deviceId not found!"));
        if(device == null){
            return Page.empty();
        }else{
            PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
            List<UserDTO> userDTOS = device.getUsers()
                    .stream().map(userMapper::toUserDTO).collect(Collectors.toList());
            return new PageImpl<>(pagedListHolderFromRequest(pageRequest,userDTOS).getPageList());
        }

    }
    private PagedListHolder pagedListHolderFromRequest(PageRequest pageRequest, List list) {
        PagedListHolder pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort((SortDefinition) pageRequest.getSort());
        return pagedListHolder;
    }




}
