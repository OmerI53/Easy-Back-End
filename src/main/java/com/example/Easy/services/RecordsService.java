package com.example.Easy.services;

import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.mappers.RecordsMapper;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.RecordsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.RecordsRepository;
import com.example.Easy.repository.specifications.RecordsSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RecordsService {

    private final RecordsRepository recordsRepository;
    private final RecordsMapper recordsMapper;
    private final NewsService newsService;

    @Autowired
    public RecordsService(RecordsRepository recordsRepository, RecordsMapper recordsMapper, @Lazy NewsService newsService) {
        this.recordsRepository = recordsRepository;
        this.recordsMapper = recordsMapper;
        this.newsService = newsService;
    }

    @Transactional
    public void setlike(UUID userId, UUID newsId, boolean bool) {
        RecordsDTO records = recordsMapper.toRecordsDTO(recordsRepository.findByUserAndNews(userId.toString(), newsId.toString()));
        records.setPostlike(bool);
        recordsRepository.save(recordsMapper.toRecordsEntity(records));
    }

    public void setbookmark(UUID userId, UUID newsId, boolean bool) {
        RecordsDTO records = recordsMapper.toRecordsDTO(recordsRepository.findByUserAndNews(userId.toString(), newsId.toString()));
        records.setPostbookmark(bool);
        recordsRepository.save(recordsMapper.toRecordsEntity(records));
    }

    public int getLikes(UUID newsId) {
        return recordsRepository.findByNewsAndPostlike(newsId.toString(), true).size();
    }

    public int getBookmarks(UUID newsId) {
        return recordsRepository.findByNewsAndPostbookmark(newsId.toString(), true).size();
    }

    public Integer getViews(UUID newsId) {
        return recordsRepository.findByNews(newsId.toString())
                .stream().mapToInt(RecordsEntity::getRepeatedRead).sum();
    }

    @Transactional
    public RecordsDTO readRecord(UserDTO user, UUID newsId) {
        NewsDTO newsDTO = newsService.getNewsById(newsId);
        RecordsDTO records = recordsMapper.toRecordsDTO(recordsRepository.findByUserAndNews(user.getUserId().toString(), newsId.toString()));
        if (records != null) {
            records.setRepeatedRead(records.getRepeatedRead() + 1);
            recordsRepository.save(recordsMapper.toRecordsEntity(records));
            return records;
        }
        RecordsDTO rerecords = RecordsDTO.builder()
                .user(user)
                .news(newsDTO)
                .newsCategory(newsDTO.getCategory())
                .repeatedRead(1)
                .build();
        recordsRepository.save(recordsMapper.toRecordsEntity(rerecords));
        return rerecords;

    }

    public List<RecordsDTO> getUserRecords(UUID userId) {
        return recordsRepository.findByUser(userId.toString())
                .stream().map(recordsMapper::toRecordsDTO)
                .collect(Collectors.toList());
    }

    public List<RecordsDTO> getLikedNews(UUID userId) {
        return recordsRepository.findByUserAndPostlike(userId.toString(), true)
                .stream().map(recordsMapper::toRecordsDTO)
                .collect(Collectors.toList());
    }

    public List<RecordsDTO> getBookmarkedNews(UserDTO userDTO) {
        return recordsRepository.findByUserAndPostbookmark(userDTO.getUserId().toString(), true)
                .stream().map(recordsMapper::toRecordsDTO)
                .collect(Collectors.toList());
    }

    public List<RecordsDTO> getUserRecords(UUID userId,Boolean likes, Boolean bookmarks) {
        return recordsRepository.findAll(RecordsSpecifications.getSpecifiedRecords(userId,likes,bookmarks))
                .stream().map(recordsMapper::toRecordsDTO)
                .collect(Collectors.toList());
    }
}
