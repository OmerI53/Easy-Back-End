package com.example.Easy.services;

import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.RecordsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.dao.UserDao;
import com.example.Easy.requests.CreateReadRequest;
import com.example.Easy.requests.CreateUserRequest;
import com.example.Easy.requests.FollowRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final ImageService imageService;
    private final AuthenticationService authenticationService;
    private final NotificationService notificationService;
    private final RecordsService recordsService;
    private final PasswordEncoder passwordEncoder;
    private final ResourceBundleMessageSource source;


    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    private final static String DEFAULT_SORT = "name";

    @Transactional
    public Map<String, String> register(CreateUserRequest createUserRequest) {
        Map<String, String> retMap = new HashMap<>();
        String imageUrl = "";
        UUID userId = null;
        if (createUserRequest.getImage() != null) {
            try {
                imageUrl = imageService.uploadImage(createUserRequest.getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        UserDTO user = UserDTO.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .image(imageUrl)
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .build();
        userId = userDao.save(user).getUserId();

        String jwt = authenticationService.generateJTW(user);
        retMap.put("imageUrl", imageUrl);
        retMap.put("jwt", jwt);
        retMap.put("userId", userId.toString());
        return retMap;
    }

    public Page<UserDTO> listUsers(String name, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        return userDao.getAll(pageRequest);
    }

    public UserDTO getUser(UUID userId) {
        return userDao.get(userId);
    }
    public UserDTO getUserByName(String name) {
        UserDTO user = userDao.get(name);
        if(user==null)
            throw new NullPointerException(source.getMessage("user.notfound", null, LocaleContextHolder.getLocale()));
        return user;
    }

    @Transactional
    public UserDTO deleteUser(UUID userId) {
        UserDTO user = getUser(userId);
        return userDao.delete(user);
    }

    @Transactional
    public UserDTO patchUserById(UUID userId, CreateUserRequest request) throws IOException {
        UserDTO user = getUser(userId);
        if (request.getName() != null && !request.getName().equals(""))
            user.setName(request.getName());
        if (request.getImage() != null)
            user.setImage(imageService.uploadImage(request.getImage()));
        if (request.getPassword() != null && !request.getPassword().equals(""))
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRole() != null)
            user.setRole(request.getRole());
        if(user.getNews()!=null)
            user.getNews().forEach(x->x.setAuthor(user));
        return userDao.update(user);
    }

    @Transactional
    public FollowRequest followUser(FollowRequest followRequest) {
        UserDTO userFollowing = getUser(followRequest.getUserId());
        UserDTO userFollowed = getUser(followRequest.getFollowingUserId());
        boolean notExisting = userFollowing.getFollowing().add(userFollowed);
        if (notExisting) {
            userFollowed.getFollowers().add(userFollowing);
            userDao.update(userFollowing);
            userDao.update(userFollowed);
            notificationService.sendFollowNotification(userFollowing, userFollowed);
        }
        return followRequest;
    }

    public FollowRequest unfollowUser(FollowRequest followRequest) {
        UserDTO userFollowing = getUser(followRequest.getUserId());
        UserDTO userFollowed = getUser(followRequest.getFollowingUserId());
        userFollowing.getFollowing().remove(userFollowed);
        userFollowed.getFollowers().remove(userFollowing);
        userDao.update(userFollowing);
        userDao.update(userFollowed);
        return followRequest;
    }

    public Page<UserDTO> getAllFollowers(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO user = getUser(userId);
        List<UserDTO> users = user.getFollowers().stream().toList();
        return new PageImpl<>(pagedListHolderFromRequestUser(pageRequest, users).getPageList());

    }

    public Page<UserDTO> getAllFollowing(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO user = getUser(userId);
        List<UserDTO> users = user.getFollowing().stream().toList();
        return new PageImpl<UserDTO>(pagedListHolderFromRequestUser(pageRequest, users).getPageList());
    }

    public RecordsDTO readNews(CreateReadRequest request) {
        UserDTO user = getUser(request.getUserId());
        return recordsService.readRecord(user, request.getNewsId());
    }

    public Page<RecordsDTO> getUserRecords(UUID userId, Integer pageNumber, Integer pageSize, String sortBy,
                                           Boolean likes, Boolean bookmarks) {
        //Default sortby is only for user
        if (sortBy == null || sortBy.equals(""))
            sortBy = "recordId";
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO user = getUser(userId);
        return new PageImpl<>(pagedListHolderFromRequestRecords(pageRequest, recordsService.getUserRecords(userId,likes,bookmarks)).getPageList());
    }

    public UserDTO getUserByEmail(String email) {
        return userDao.getByEmail(email);
    }


    public Page<NewsDTO> getNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO users = getUser(userId);
        List<NewsDTO> news = users.getNews();
        return new PageImpl<NewsDTO>(pagedListHolderFromRequestNews(pageRequest, news).getPageList());

    }

    public Page<DeviceDTO> getDevices(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO users = getUser(userId);
        List<DeviceDTO> devices = users.getDevices();
        return new PageImpl<DeviceDTO>(pagedListHolderFromRequestDevice(pageRequest, devices).getPageList());
    }

    @Transactional
    public void addDevice(UUID userId, DeviceDTO device) {
        UserDTO user = getUser(userId);
        user.getDevices().add(device);
        userDao.save(user);
    }

    public void deleteDevice(UUID userId, DeviceDTO device) {
        UserDTO user = getUser(userId);
        user.getDevices().remove(device);
        userDao.save(user);
    }

    /*
    public Page<NewsDTO> getFollowingNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        UserDTO user = userRepository.findById(userId).orElse(null);
        List<UserDTO> news = user.getFollowing()
                .stream().flatMap(x->x.getNews().stream())
                .collect(Collectors.toList());
        return new PageImpl<>(pagedListHolderFromRequest(pageRequest,news.stream().map(newsMapper::toNewsDTO).collect(Collectors.toList())).getPageList());
    }
     */
    //-----Page builders-----
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

    private PagedListHolder<UserDTO> pagedListHolderFromRequestUser(PageRequest pageRequest, List<UserDTO> list) {
        PagedListHolder<UserDTO> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(), true, true));
        return pagedListHolder;
    }

    private PagedListHolder<NewsDTO> pagedListHolderFromRequestNews(PageRequest pageRequest, List<NewsDTO> list) {
        PagedListHolder<NewsDTO> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(), true, true));
        return pagedListHolder;
    }

    private PagedListHolder<DeviceDTO> pagedListHolderFromRequestDevice(PageRequest pageRequest, List<DeviceDTO> list) {
        PagedListHolder<DeviceDTO> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(), true, true));
        return pagedListHolder;
    }

    private PagedListHolder<RecordsDTO> pagedListHolderFromRequestRecords(PageRequest pageRequest, List<RecordsDTO> list) {
        PagedListHolder<RecordsDTO> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(), true, true));
        return pagedListHolder;
    }


}
