package com.example.Easy.Services;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Mappers.CommentMapper;
import com.example.Easy.Models.CommentDTO;
import com.example.Easy.Repository.CommentRepository;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

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

    public CommentDTO getCommentById(UUID commentId) {
        return commentMapper.toCommentDTO(commentRepository.findById(commentId).orElse(null));
    }

    public void deleteCommentById(UUID commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        commentRepository.delete(commentEntity);
    }

    public CommentDTO patchCommentById(UUID commentId,CommentDTO commentDTO) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        if(commentDTO.getText()!=null || !commentDTO.getText().equals(""))
            commentEntity.setText(commentDTO.getText());
        return commentMapper.toCommentDTO(commentRepository.save(commentEntity));
    }
}
