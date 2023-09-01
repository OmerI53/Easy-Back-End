package com.example.Easy.Services;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.NotificationEntity;
import com.example.Easy.Entities.RecordsEntity;
import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Mappers.RecordsMapper;
import com.example.Easy.Mappers.UserMapper;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Models.RecordsDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Repository.NotificationRepository;
import com.example.Easy.Repository.RecordsRepository;
import com.example.Easy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RecordsRepository recordsRepository;
    private final RecordsMapper recordsMapper;

    public UserDTO createNewUser(UserDTO userDTO){
        //TODO cant since a real FCM token is needed
        //notificationService.subscribeToTopic("All",userDTO.getUserToken());
        UserEntity user = userRepository.save(userMapper.toUserEntity(userDTO));
        NewTopic topic = TopicBuilder.name(user.getUserId().toString()).build();
        return userMapper.toUserDTO(user);
    }
    private final static int DEFAULT_PAGE=0;
    private final static int DEFAULT_PAGE_SIZE=25;
    private final static String DEFAULT_SORT="name";
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
    public void deleteUser(UUID userId){
        userRepository.deleteById(userId);
    }

    public Page<UserDTO> listUsers(Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        return userRepository.findAll(pageRequest).map(userMapper::toUserDTO);
    }

    public UserDTO getUserById(UUID userId) {
        return userMapper.toUserDTO(userRepository.findById(userId).orElse(null));
    }
    public void patchUserById(UUID userId, UserDTO userDTO) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if(user==null)
                return;
        if(userDTO.getUserToken()!=null && !userDTO.getUserToken().equals(""))
            user.setUserToken(userDTO.getUserToken());
        if(userDTO.getName()!=null && !userDTO.getName().equals(""))
            user.setName(userDTO.getName());
        if(userDTO.getImage()!=null && !userDTO.getName().equals(""))
            user.setImage(userDTO.getImage());
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
            userRepository.save(userFollowing);
            userRepository.save(userFollowed);
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
        userFollowing.getFollowing().remove(userFollowed);
        userFollowed.getFollowers().remove(userFollowing);
        userRepository.save(userFollowing);
        userRepository.save(userFollowed);
    }

    public Page<UserDTO> getAllFollowers(UUID userId, Integer pageNumber, Integer pageSize, String sortBy){
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        UserEntity user = userRepository.findById(userId).orElse(null);
        List<UserDTO> users = user.getFollowers()
                .stream().map(userMapper::toUserDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(users,pageRequest,users.size());

    }

    public Page<UserDTO> getAllFollowing(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        UserEntity user = userRepository.findById(userId).orElse(null);
        List<UserDTO> users= user.getFollowing()
                .stream().map(userMapper::toUserDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(users,pageRequest,users.size());
    }


    public void readNews(UUID userId, NewsDTO newsDTO) {
        // TODO definitely needs to be optimized
        UserEntity user = userRepository.findById(userId).orElse(null);
        NewsEntity news = newsRepository.findById(newsDTO.getNewsId()).orElse(null);
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);
        if(records!=null) {
            records.setRepeatedRead(records.getRepeatedRead()+1);
            recordsRepository.save(records);
        }else {
            assert news != null;
            RecordsEntity rerecords = RecordsEntity.builder()
                    .user(user)
                    .news(news)
                    .newsCategory(news.getCategory())
                    .repeatedRead(1)
                    .build();
            recordsRepository.save(rerecords);
        }
    }

    public Page<RecordsDTO> getUserRecordsById(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        //Default sortby is only for user
        if(sortBy==null || sortBy.equals(""))
            sortBy="recordId";
        UserEntity user = userRepository.findById(userId).orElse(null);
        List<RecordsDTO> recordsEntityList = recordsRepository.findByUser(user)
                .stream().map(recordsMapper::toRecordsDTO)
                .collect(Collectors.toList());
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        return new PageImpl<>(recordsEntityList,pageRequest,recordsEntityList.size());
    }
}
