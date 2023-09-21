package com.example.Easy.services;

import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.RecordsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.dao.RecordsDao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class RecordsService {

    private final RecordsDao recordsDao;
    private final NewsService newsService;

    @Autowired
    public RecordsService(RecordsDao recordsDao, @Lazy NewsService newsService) {
        this.recordsDao = recordsDao;
        this.newsService = newsService;
    }

    @Transactional
    public void setlike(UUID userId, UUID newsId, boolean bool) {
        RecordsDTO records = recordsDao.get(userId,newsId);
        records.setPostlike(bool);
        recordsDao.save(records);
    }

    public void setbookmark(UUID userId, UUID newsId, boolean bool) {
        RecordsDTO records = recordsDao.get(userId,newsId);
        records.setPostbookmark(bool);
        recordsDao.save(records);
    }

    @Transactional
    public RecordsDTO readRecord(UserDTO user, UUID newsId) {
        NewsDTO newsDTO = newsService.getNewsById(newsId);
        RecordsDTO records = recordsDao.get(user.getUserId(),newsId);
        if (records != null) {
            records.setRepeatedRead(records.getRepeatedRead() + 1);
            return recordsDao.update(records);
        }
        RecordsDTO newRecords = RecordsDTO.builder()
                .user(user)
                .news(newsDTO)
                .newsCategory(newsDTO.getCategory())
                .repeatedRead(1)
                .build();
        return recordsDao.save(newRecords);

    }
    public List<RecordsDTO> getUserRecords(UUID userId,Boolean likes, Boolean bookmarks) {
        return recordsDao.getByUser(userId,likes,bookmarks);
    }
}
