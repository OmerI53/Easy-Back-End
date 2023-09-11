package com.example.Easy.Services;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Models.CommentDTO;
import com.example.Easy.Repository.CommentRepository;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;


    public void postComment(CommentDTO commentDTO) {
        UserEntity user = userRepository.findById(commentDTO.getAuthor().getUserId()).orElse(null);
        NewsEntity news = newsRepository.findById(commentDTO.getNewsId()).orElse(null);
        if(user==null)
            throw new NullPointerException("user is null");
        if(news==null)
            throw new NullPointerException("news is null");
        CommentEntity commentEntity = CommentEntity.builder()
                .news(news)
                .author(user)
                .text(commentDTO.getText())
                .build();
        commentRepository.save(commentEntity);
    }
}
