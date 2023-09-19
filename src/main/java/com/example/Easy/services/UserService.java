package com.example.Easy.services;

import com.example.Easy.mappers.UserMapper;
import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.RecordsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.UserRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
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
        userId = userRepository.save(userMapper.toUserEntity(user)).getUserId();

        String jwt = authenticationService.generateJTW(user);
        retMap.put("imageUrl", imageUrl);
        retMap.put("jwt", jwt);
        retMap.put("userId", userId.toString());
        return retMap;
    }

    public Page<UserDTO> listUsers(String name, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        if (name == null || name.equals(""))
            return userRepository.findAll(pageRequest).map(userMapper::toUserDTO);
        return userRepository.findByName(name, pageRequest).map(userMapper::toUserDTO);
    }

    public UserDTO getUserById(UUID userId) {
        return userMapper.toUserDTO(userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException(source.getMessage("user.notfound", null, LocaleContextHolder.getLocale()))));
    }

    @Transactional
    public UserDTO deleteUser(UUID userId) {
        UserDTO user = getUserById(userId);
        userRepository.deleteById(userId);
        return user;
    }

    @Transactional
    public UserDTO patchUserById(UUID userId, CreateUserRequest request) throws IOException {
        UserDTO user = getUserById(userId);
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
        return userMapper.toUserDTO(userRepository.save(userMapper.toUserEntity(user)));
    }

    @Transactional
    public FollowRequest followUser(FollowRequest followRequest) {
        UserDTO userFollowing = getUserById(followRequest.getUserId());
        UserDTO userFollowed = getUserById(followRequest.getFollowingUserId());
        boolean notExisting = userFollowing.getFollowing().add(userFollowed);
        if (notExisting) {
            userFollowed.getFollowers().add(userFollowing);
            userRepository.save(userMapper.toUserEntity(userFollowing));
            userRepository.save(userMapper.toUserEntity(userFollowed));
            notificationService.sendFollowNotification(userFollowing, userFollowed);
        }
        return followRequest;
    }

    public FollowRequest unfollowUser(FollowRequest followRequest) {
        UserDTO userFollowing = getUserById(followRequest.getUserId());
        UserDTO userFollowed = getUserById(followRequest.getFollowingUserId());
        userFollowing.getFollowing().remove(userFollowed);
        userFollowed.getFollowers().remove(userFollowing);
        userRepository.save(userMapper.toUserEntity(userFollowing));
        userRepository.save(userMapper.toUserEntity(userFollowed));
        return followRequest;
    }

    public Page<UserDTO> getAllFollowers(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO user = getUserById(userId);
        List<UserDTO> users = user.getFollowers().stream().toList();
        return new PageImpl<>(pagedListHolderFromRequestUser(pageRequest, users).getPageList());

    }

    public Page<UserDTO> getAllFollowing(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO user = getUserById(userId);
        List<UserDTO> users = user.getFollowing().stream().toList();
        return new PageImpl<UserDTO>(pagedListHolderFromRequestUser(pageRequest, users).getPageList());
    }

    public RecordsDTO readNews(CreateReadRequest request) {
        UserDTO user = getUserById(request.getUserId());
        return recordsService.readRecord(user, request.getNewsId());
    }

    public Page<RecordsDTO> getUserRecordsById(UUID userId, Integer pageNumber, Integer pageSize, String sortBy,
                                               Boolean likes, Boolean bookmarks) {
        //Default sortby is only for user
        if (sortBy == null || sortBy.equals(""))
            sortBy = "recordId";

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO user = getUserById(userId);


        boolean finalLikes = likes != null && likes;
        boolean finalBookmarks = bookmarks != null && bookmarks;
        return new PageImpl<>(pagedListHolderFromRequestRecords(pageRequest, recordsService.getUserRecords(user.getUserId())
                .stream()
                .filter(x -> (x.isPostlike() || (!x.isPostlike() & !finalLikes)))
                .filter(x -> (x.isPostbookmark() || (!x.isPostbookmark() & !finalBookmarks)))
                .collect(Collectors.toList())).getPageList());

        /*
        if (likes == null && bookmarks == null)
            return new PageImpl<>(pagedListHolderFromRequestRecords(pageRequest, recordsService.getUserRecords(user.getUserId())).getPageList());
        if (likes != null && likes == true)
            return new PageImpl<>(pagedListHolderFromRequestRecords(pageRequest, recordsService.getUserRecords(user.getUserId())
                    .stream().filter(RecordsDTO::isPostlike).collect(Collectors.toList())).getPageList());
        if (bookmarks != null && bookmarks == true)
            return new PageImpl<>(pagedListHolderFromRequestRecords(pageRequest, recordsService.getUserRecords(user.getUserId())
                    .stream().filter(RecordsDTO::isPostbookmark).collect(Collectors.toList())).getPageList());
        return new PageImpl<>(pagedListHolderFromRequestRecords(pageRequest, recordsService.getUserRecords(user.getUserId())
                .stream().filter(x -> x.isPostbookmark() && x.isPostlike()).collect(Collectors.toList())).getPageList());
         */
    }

    public UserDTO getUserByEmail(String email) {
        return userMapper.toUserDTO(userRepository.findByEmail(email));
    }

    public Page<NewsDTO> getLikedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO user = getUserById(userId);
        List<NewsDTO> newsDTOS = recordsService.getLikedNews(user.getUserId())
                .stream().map(x -> x.getNews()).collect(Collectors.toList());
        return new PageImpl<NewsDTO>(pagedListHolderFromRequestNews(pageRequest, newsDTOS).getPageList());
    }

    public Page<NewsDTO> getBookmarkedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO user = getUserById(userId);
        List<NewsDTO> newsDTOS = recordsService.getBookmarkedNews(user)
                .stream().map(x -> x.getNews()).collect(Collectors.toList());
        return new PageImpl<NewsDTO>(pagedListHolderFromRequestNews(pageRequest, newsDTOS).getPageList());
    }

    public Page<NewsDTO> getNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO users = getUserById(userId);
        List<NewsDTO> news = users.getNews();
        return new PageImpl<NewsDTO>(pagedListHolderFromRequestNews(pageRequest, news).getPageList());

    }

    public Page<DeviceDTO> getDevices(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserDTO users = getUserById(userId);
        List<DeviceDTO> devices = users.getDevices();
        return new PageImpl<DeviceDTO>(pagedListHolderFromRequestDevice(pageRequest, devices).getPageList());
    }

    @Transactional
    public void addDevice(UUID userId, DeviceDTO device) {
        UserDTO user = getUserById(userId);
        user.getDevices().add(device);
        userRepository.save(userMapper.toUserEntity(user));
    }

    public void deleteDevice(UUID userId, DeviceDTO device) {
        UserDTO user = getUserById(userId);
        user.getDevices().remove(device);
        userRepository.save(userMapper.toUserEntity(user));
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
