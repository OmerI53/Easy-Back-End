package com.example.Easy.Services;

import com.example.Easy.Entities.CommentEntity;
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
        CommentEntity commentEntity = CommentEntity.builder()
                .news(newsRepository.findById(commentDTO.getNewsId()).orElse(null))
                .author(userRepository.findById(commentDTO.getAuthor().getUserId()).orElse(null))
                .text(commentDTO.getText())
                .build();
        commentRepository.save(commentEntity);
    }
}
