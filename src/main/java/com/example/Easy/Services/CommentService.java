package com.example.Easy.Services;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Mappers.CommentMapper;
import com.example.Easy.Mappers.UserMapper;
import com.example.Easy.Models.CommentDTO;
import com.example.Easy.Repository.CommentRepository;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
   CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    public void postComment(CommentDTO commentDTO) {
        CommentEntity commentEntity = CommentEntity.builder()
                .news(newsRepository.findById(commentDTO.getNewsId()).orElse(null))
                .author(userRepository.findById(commentDTO.getAuthor().getUserId()).orElse(null))
                .text(commentDTO.getText())
                .build();
        commentRepository.save(commentEntity);
    }
}
