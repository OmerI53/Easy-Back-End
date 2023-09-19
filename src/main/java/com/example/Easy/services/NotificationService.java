package com.example.Easy.services;

import com.example.Easy.entities.NotificationEntity;
import com.example.Easy.mappers.NotificationMapper;
import com.example.Easy.models.NotificationDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.NotificationRepository;
import com.example.Easy.requests.CreateNotificationRequest;
import com.example.Easy.requests.CreateSubscriptionRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final FirebaseMessaging firebaseMessaging;
    private final ResourceBundleMessageSource source;


    private final static int DEFAULT_PAGE=0;
    private final static int DEFAULT_PAGE_SIZE=25;
    private final static String DEFAULT_SORT="text";

    @Transactional
    public NotificationDTO sendNotification(CreateNotificationRequest createNotificationRequest)  {
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .title(createNotificationRequest.getTitle())
                .text(createNotificationRequest.getText())
                .topic(createNotificationRequest.getTopic())
                .build();
        notificationRepository.save(notificationMapper.toNotificationEntity(notificationDTO));

        //build notification from notificationDTO
        Notification notification = Notification.builder()
                .setTitle(notificationDTO.getTitle())
                .setBody(notificationDTO.getText())
                .build();
        //build message by using notification and a recipient token
        Message message = Message.builder()
                .setTopic(notificationDTO.getTopic())
                .setNotification(notification)
                .build();
        //firebase handles the sending procedure
        try {
             firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        return notificationDTO;
    }
    public CreateSubscriptionRequest subscribeToTopic(CreateSubscriptionRequest request) {
        try {
            firebaseMessaging.subscribeToTopic(Arrays.asList(request.getFcmToken()),request.getTopic());
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        return request;
    }
    public void subscribeToTopic(String topic,List<String> tokens)  {
        try {
            firebaseMessaging.subscribeToTopic(tokens,topic);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public CreateSubscriptionRequest unsubscribeToTopic(CreateSubscriptionRequest request)  {
        try {
            firebaseMessaging.unsubscribeFromTopic(Arrays.asList(request.getTopic()), request.getTopic());
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
        return request;
    }
    public void unsubscribeToTopic(String topic,List<String> tokens) {
        try {
            firebaseMessaging.unsubscribeFromTopic(tokens,topic);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public Page<NotificationDTO> getMessageByTitle(String title, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        return notificationRepository.getNotificationByTitle(title,pageRequest).map(notificationMapper::toNotificationDTO);
    }

    public void sendFollowNotification(UserDTO userFollowing, UserDTO userFollowed) {
        NotificationEntity notificationEntity = NotificationEntity.builder()
                .text(userFollowing.getName() + source.getMessage("notification.follow",null, LocaleContextHolder.getLocale()))
                .title("follow")
                .build();
        kafkaTemplate.send("Follow", userFollowed.getUserId().toString() + ":" + userFollowing.getName());
    }
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
}
