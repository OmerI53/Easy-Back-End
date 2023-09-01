package com.example.Easy.Services;

import com.example.Easy.Entites.NewsEntity;
import com.example.Easy.Mappers.NewsMapper;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private NewsRepository newsRepository;
    private NewsMapper newsMapper;

    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll()
                .stream().map(newsMapper::toNewsDTO)
                .collect(Collectors.toList());
    }

    public NewsDTO getNewsByID(UUID id){

        return newsMapper.toNewsDTO(newsRepository.findById(id).orElse(null));
    }
    public List<NewsDTO> getNewsByTitle(String title){
        return newsRepository.findByTitle(title)
                .stream().map(newsMapper::toNewsDTO)
                .collect(Collectors.toList());
    }



}
