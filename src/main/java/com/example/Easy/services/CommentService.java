package com.example.Easy.services;

import com.example.Easy.dao.CommentDao;
import com.example.Easy.entities.CommentEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.mappers.CommentMapper;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.repository.CommentRepository;
import com.example.Easy.repository.NewsRepository;
import com.example.Easy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentDao {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final UserRepository userRepository;

    private final NewsRepository newsRepository;


    public void post(CommentDTO commentDTO) {
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

    public CommentDTO get(UUID commentId) {
        return CommentMapper.toCommentDTO(commentRepository.findById(commentId).orElse(null));
    }

    public void delete(UUID commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        assert commentEntity != null;
        commentRepository.delete(commentEntity);
    }

    public CommentDTO patch(UUID commentId,CommentDTO commentDTO) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElse(null);
        if(commentDTO.getText()!=null || !commentDTO.getText().equals("")) {
            assert commentEntity != null;
            commentEntity.setText(commentDTO.getText());
        }
        assert commentEntity != null;
        return CommentMapper.toCommentDTO(commentRepository.save(commentEntity));
    }
}
