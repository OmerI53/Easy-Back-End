package com.example.Easy.Services;

import com.example.Easy.Mappers.DeviceMapper;
import com.example.Easy.Mappers.NewsMapper;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Repository.DeviceRepository;
import com.example.Easy.Repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public void deleteNews(NewsDTO newsDTO){

        newsRepository.delete(newsMapper.toNewsEntity(newsDTO));

    }

    public void addNews(NewsDTO newsDTO){

        newsRepository.save(newsMapper.toNewsEntity(newsDTO));
    }
}
