package com.example.Easy.repository.dao;

import com.example.Easy.mappers.NotificationMapperImpl;
import com.example.Easy.models.NotificationDTO;
import com.example.Easy.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
@RequiredArgsConstructor
public class NotificationDao implements Dao<NotificationDTO> {

    private final NotificationRepository notificationRepository;
    private final NotificationMapperImpl notificationMapper;
    private final ResourceBundleMessageSource source;

    @Override
    public NotificationDTO get(UUID id) {
        return notificationMapper.toNotificationDTO(notificationRepository.findById(id)
                .orElseThrow(()-> new NullPointerException(source.getMessage("notification.notfound",null, LocaleContextHolder.getLocale()))));
    }

    @Override
    public List<NotificationDTO> getAll() {
        return null;
    }

    @Override
    public NotificationDTO save(NotificationDTO notificationDTO) {
        return notificationMapper.toNotificationDTO(notificationRepository.save(notificationMapper.toNotificationEntity(notificationDTO)));
    }

    @Override
    public NotificationDTO update(NotificationDTO notificationDTO) {
        return null;
    }

    @Override
    public NotificationDTO delete(NotificationDTO notificationDTO) {
        return null;
    }

    public Page<NotificationDTO> get(String title, PageRequest pageRequest) {
        return notificationRepository.getNotificationByTitle(title,pageRequest).map(notificationMapper::toNotificationDTO);

    }
}
