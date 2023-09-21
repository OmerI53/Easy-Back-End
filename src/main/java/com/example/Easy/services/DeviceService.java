package com.example.Easy.services;

import com.example.Easy.models.AuthenticatedUserDTO;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.DeviceType;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.dao.DeviceDao;
import com.example.Easy.requests.CreateDeviceRequest;
import com.example.Easy.requests.LoginRequest;
import com.example.Easy.requests.LogoutRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceDao deviceDao;
    private final NotificationService notificationService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ResourceBundleMessageSource source;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    private final static String DEFAULT_SORT = "timeZone";

    public DeviceDTO getDevice(UUID deviceId) {
         return deviceDao.get(deviceId);
    }

    @Transactional
    public DeviceDTO addDevice(CreateDeviceRequest createDeviceRequest)  {
        //TODO cant bootstrap data since a real FCM is needed
        DeviceDTO device = DeviceDTO.builder()
                .deviceToken(createDeviceRequest.getDeviceToken())
                .deviceType(createDeviceRequest.getDeviceType())
                .timeZone(createDeviceRequest.getTimeZone())
                .build();
        notificationService.subscribeToTopic("All", List.of(device.getDeviceToken()));
        return deviceDao.save(device);
    }
    @Transactional
    public DeviceDTO removeDeviceById(UUID deviceId) {
        DeviceDTO deviceDTO = getDevice(deviceId);
        return deviceDao.delete(deviceDTO);
    }

    public Page<DeviceDTO> listAllDevices(Integer pageNumber, Integer pageSize, String sortBy,
                                          String timeZone, DeviceType deviceType) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        return deviceDao.getAll(timeZone,deviceType,pageRequest);
    }

    @Transactional
    public DeviceDTO patchDevice(UUID deviceId, CreateDeviceRequest createDeviceRequest) {
        DeviceDTO deviceDTO = getDevice(deviceId);
        if (createDeviceRequest.getDeviceToken() != null & !Objects.equals(createDeviceRequest.getDeviceToken(), ""))
            deviceDTO.setDeviceToken(createDeviceRequest.getDeviceToken());
        if (createDeviceRequest.getTimeZone() != null & !Objects.equals(createDeviceRequest.getDeviceToken(), ""))
            deviceDTO.setTimeZone(createDeviceRequest.getTimeZone());
        if (createDeviceRequest.getDeviceType() != null)
            deviceDTO.setDeviceType(createDeviceRequest.getDeviceType());
        return deviceDao.update(deviceDTO);
    }
    @Transactional
    public AuthenticatedUserDTO loginToDevice(LoginRequest loginRequest) {
        UserDTO user = userService.getUserByEmail(loginRequest.getEmail());
        AuthenticatedUserDTO auth = authenticationService.authenticate(loginRequest, user);
        DeviceDTO device = getDevice(loginRequest.getDeviceId());
        if (!device.getUsers().contains(user)) {
            device.getUsers().add(user);
            deviceDao.update(device);
            userService.addDevice(user.getUserId(),device);
        }
        notificationService.subscribeToTopic(user.getUserId().toString(), List.of(device.getDeviceToken()));
        return auth;
    }

    @Transactional
    public void logoutFromDevice(LogoutRequest logoutRequest) {
        DeviceDTO device = getDevice(logoutRequest.getDeviceId());
        UserDTO user = userService.getUser(logoutRequest.getUserId());
        userService.deleteDevice(user.getUserId(), device);
        device.getUsers().remove(user);
        notificationService.unsubscribeToTopic(user.getUserId().toString(), List.of(device.getDeviceToken()));
        deviceDao.save(device);
    }

    public Page<UserDTO> getDeviceUsers(UUID deviceId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        DeviceDTO deviceDTO = getDevice(deviceId);
        List<UserDTO> users = deviceDTO.getUsers();
        return new PageImpl<>(pagedListHolderFromRequest(pageRequest, users).getPageList());
    }

    private PagedListHolder pagedListHolderFromRequest(PageRequest pageRequest, List list) {
        PagedListHolder pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(), true, true));
        return pagedListHolder;
    }

    /* //TODO will be re-implemended later
    public String switchAccount(UUID userId,DeviceDTO deviceDTO) {
        DeviceEntity device = deviceRepository.findById(deviceDTO.getDeviceID()).orElse(null);
        UserEntity user = userRepository.findById(userId).orElse(null);
        if(user!=null && device.getUsers().contains(user))
            return authenticationService.generateJTW(user);
        return "";
    }
 */
    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String sortBy) {
        // if not initilized set it to default
        int queryPageNumber;
        int queryPageSize;
        String querySortBy;

        if (pageNumber != null && pageNumber > 0)
            queryPageNumber = pageNumber - 1; //it is 0 indexed, for first page, number is 1.
        else
            queryPageNumber = DEFAULT_PAGE;

        if (pageSize == null)
            queryPageSize = DEFAULT_PAGE_SIZE;
        else
            queryPageSize = pageSize;
        //setting a max size
        if (queryPageSize > 100)
            queryPageSize = 100;

        if (sortBy != null && !sortBy.equals(""))
            querySortBy = sortBy;
        else
            querySortBy = DEFAULT_SORT;

        Sort sort = Sort.by(Sort.Order.desc(querySortBy));
        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }


}
