package com.example.Easy.Services;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Mappers.CommentMapper;
import com.example.Easy.Models.CommentDTO;
import com.example.Easy.Repositories.CommentRepository;
import com.example.Easy.Repositories.NewsRepository;
import com.example.Easy.Repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

    private  CommentRepository commentRepository;
    private  CommentMapper commentMapper;
    private  UserRepository userRepository;
    private  NewsRepository newsRepository;


    public void sendComment(CommentDTO commentDTO){
        CommentEntity commentEntity = CommentEntity.builder()
                .news(newsRepository.findById(commentDTO.getNews().getNewsUUID()).orElse(null))
                .author(userRepository.findById(commentDTO.getAuthor().getUserId()).orElse(null))
                .text(commentDTO.getText())
                .build();
        commentRepository.save(commentMapper.toCommentEntity(commentDTO));
    }
}
