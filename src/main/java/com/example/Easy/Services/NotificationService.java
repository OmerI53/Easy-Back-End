package com.example.Easy.Services;

import com.example.Easy.Mappers.NotificationMapper;
import com.example.Easy.Models.NotificationDTO;
import com.example.Easy.Repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private NotificationRepository notificationRepository;
    private NotificationMapper notificationMapper;
    public List<NotificationDTO> getAllNews(){
        return notificationRepository.findAll().stream().map(notificationMapper::toNotificationDto)
                        .collect(Collectors.toList());
    }
    public void getNotificationByID(UUID uuid){
        notificationMapper.toNotificationDto(notificationRepository.getReferenceById(uuid));
    }


}
