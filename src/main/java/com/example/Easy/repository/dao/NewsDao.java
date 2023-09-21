package com.example.Easy.repository.dao;

import com.example.Easy.mappers.NewsMapper;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.repository.NewsRepository;
import com.example.Easy.repository.specifications.NewsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Repository
@RequiredArgsConstructor
public class NewsDao implements Dao<NewsDTO> {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final ResourceBundleMessageSource source;

    @Override
    public NewsDTO get(UUID id) {
        return newsMapper.toNewsDTO(newsRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(source.getMessage("news.notfound", null, LocaleContextHolder.getLocale()))));
    }

    @Override
    public List<NewsDTO> getAll() {
        return newsRepository.findAll()
                .stream().map(newsMapper::toNewsDTO)
                .collect(Collectors.toList());

    }
    public Page<NewsDTO> getAll(String category, String title, String authorName, PageRequest pageRequest) {
        return newsRepository.findAll(NewsSpecifications.getSpecifiedNews(category,title,authorName),pageRequest)
                .map(newsMapper::toNewsDTO);

    }

    @Override
    public NewsDTO save(NewsDTO newsDTO) {
        return newsMapper.toNewsDTO(newsRepository.save(newsMapper.toNewsEntity(newsDTO)));
    }

    @Override
    public NewsDTO update(NewsDTO newsDTO) {
        return newsMapper.toNewsDTO(newsRepository.save(newsMapper.toNewsEntity(newsDTO)));
    }

    @Override
    public NewsDTO delete(NewsDTO newsDTO) {
        newsRepository.delete(newsMapper.toNewsEntity(newsDTO));
        return newsDTO;
    }
}
