package com.example.Easy.Services;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.NotificationEntity;
import com.example.Easy.Entities.RecordsEntity;
import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Mappers.DeviceMapper;
import com.example.Easy.Mappers.NewsMapper;
import com.example.Easy.Mappers.RecordsMapper;
import com.example.Easy.Mappers.UserMapper;
import com.example.Easy.Models.DeviceDTO;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Models.RecordsDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Repository.NotificationRepository;
import com.example.Easy.Repository.RecordsRepository;
import com.example.Easy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final RecordsRepository recordsRepository;
    private final RecordsMapper recordsMapper;

    private final DeviceMapper deviceMapper;

    private final PasswordEncoder passwordEncoder;


    private final static int DEFAULT_PAGE=0;
    private final static int DEFAULT_PAGE_SIZE=25;
    private final static String DEFAULT_SORT="name";


    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String sortBy) {
        pageNumber = pageNumber != null && pageNumber > 0 ? pageNumber - 1 : DEFAULT_PAGE;
        pageSize = pageSize != null && pageSize > 0 && pageSize <= 100 ? pageSize : DEFAULT_PAGE_SIZE;
        sortBy = sortBy != null && !sortBy.isEmpty() ? sortBy : DEFAULT_SORT;

        Sort sort = Sort.by(Sort.Order.desc(sortBy));
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public void deleteUser(UUID userId){
        userRepository.deleteById(userId);
    }
    public Page<UserDTO> listUsers(Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        return userRepository.findAll(pageRequest).map(userMapper::toUserDTO);
    }
    public UserDTO getUserById(UUID userId) {
        return userRepository.findById(userId).map(userMapper::toUserDTO).orElse(null);
    }

    public void patchUserById(UUID userId, UserDTO userDTO) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if(user==null || userDTO == null)
            return;
        if(userDTO.getName()!=null && !userDTO.getName().equals(""))
            user.setName(userDTO.getName());
        if(userDTO.getImage()!=null && !userDTO.getName().equals(""))
            user.setImage(userDTO.getImage());
        if(userDTO.getPassword()!=null && !userDTO.getPassword().equals(""))
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if(userDTO.getRole()!=null)
            user.setRole(userDTO.getRole());
        userRepository.save(user);
    }

    public void followUserById(UUID userId, UserDTO userDTO) {
        UserEntity userFollowing = userRepository.findById(userDTO.getUserId()).orElse(null);
        UserEntity userFollowed = userRepository.findById(userId).orElse(null);
        boolean notExisting = userFollowing.getFollowing().add(userFollowed);
        if(notExisting) {
            userFollowed.getFollowers().add(userFollowing);
            userRepository.saveAll(Arrays.asList(userFollowing, userFollowed));
            NotificationEntity notificationEntity = NotificationEntity.builder()
                    .text(userFollowing.getName() + " has followed you")
                    .title("follow")
                    .build();
            kafkaTemplate.send("Follow", userFollowed.getUserId().toString() + ":" + userFollowing.getName());
            notificationRepository.save(notificationEntity);
        }
    }

    public void unfollowUserById(UUID userId, UserDTO userDTO) {
        UserEntity userFollowing = userRepository.findById(userDTO.getUserId()).orElse(null);
        UserEntity userFollowed = userRepository.findById(userId).orElse(null);
        if (userFollowed == null || userFollowing == null){
            System.out.println("There is no followed user!");
        }else{
            userFollowing.getFollowing().remove(userFollowed);
            userFollowed.getFollowers().remove(userFollowing);
            userRepository.saveAll(Arrays.asList(userFollowing,userFollowed));
        }
    }

    public Page<UserDTO> getAllFollowers(UUID userId, Integer pageNumber, Integer pageSize, String sortBy){
        UserEntity user = userRepository.findById(userId).orElse(null);

        if(user ==null) {
            System.out.println("empty user!");
            return Page.empty();
        }else {
            PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);

            List<UserDTO> users = user.getFollowers()
                    .stream().map(userMapper::toUserDTO)
                    .toList();

            return new PageImpl<>(pagedListHolderFromRequest(pageRequest,users).getPageList());
        }
    }

    public Page<UserDTO> getAllFollowing(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if(user ==null){
            System.out.println("empty user!");
            return Page.empty();
        }else{
            PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);

            List<UserDTO> users= user.getFollowing()
                    .stream()
                    .map(userMapper::toUserDTO)
                    .collect(Collectors.toList());

            return new PageImpl<>(pagedListHolderFromRequest(pageRequest,users).getPageList());
        }
    }
    public void readNews(UUID userId, NewsDTO newsDTO) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        NewsEntity news = newsRepository.findById(newsDTO.getNewsId()).orElse(null);
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);
        if (user == null || news == null){
            System.out.println("User or News is null!");
        }else{
            if(records!=null) {
                records.setRepeatedRead(records.getRepeatedRead()+1);
                recordsRepository.save(records);
            }else {
                RecordsEntity rerecords = RecordsEntity.builder()
                        .user(user)
                        .news(news)
                        .newsCategory(news.getCategory())
                        .repeatedRead(1)
                        .build();
                recordsRepository.save(rerecords);
            }
        }
    }

    public Page<RecordsDTO> getUserRecordsById(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        // Default sortby is only for user
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "recordId";
        }

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserEntity user = userRepository.findById(userId).orElse(null);

        return recordsRepository.findByUser(user, pageRequest)
                .map(recordsMapper::toRecordsDTO);
    }

    public UserDTO findUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null ? userMapper.toUserDTO(user) : null;
    }

    /////HATA VEREBİLİR///
    private PagedListHolder pagedListHolderFromRequest(PageRequest pageRequest, List list) {
        PagedListHolder pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort((SortDefinition) pageRequest.getSort());
        return pagedListHolder;
    }
    /////HATA VEREBİLİR///
    public Page<NewsDTO> getLikedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy){
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        UserEntity user = userRepository.findById(userId).orElse(null);

        List<NewsDTO> newsEntities = user != null
                ? recordsRepository.findByUserAndPostlike(user, true)
                .stream()
                .map(record -> newsMapper.toNewsDTO(record.getNews()))
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new PageImpl<NewsDTO>(pagedListHolderFromRequest(pageRequest,newsEntities).getPageList());
    }

    public Page<NewsDTO> getBookmarkedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy){
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        UserEntity user = userRepository.findById(userId).orElse(null);

        List<NewsDTO> newsEntities = user != null
                ? recordsRepository.findByUserAndPostbookmark(user, true)
                .stream()
                .map(record -> newsMapper.toNewsDTO(record.getNews()))
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new PageImpl<NewsDTO>(pagedListHolderFromRequest(pageRequest,newsEntities).getPageList());
    }

    public Page<DeviceDTO> getDevices(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        UserEntity users = userRepository.findById(userId).orElse(null);

        List<DeviceDTO> devices = users != null ? users.getDevice()
                .stream().map(deviceMapper::toDeviceDTO).collect(Collectors.toList())
                : Collections.emptyList();
        return new PageImpl<DeviceDTO>(pagedListHolderFromRequest(pageRequest,devices).getPageList());
    }
}
