package com.example.Easy.services;

import com.example.Easy.entities.CommentEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.repository.CommentRepository;
import com.example.Easy.repository.NewsRepository;
import com.example.Easy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;


    public void postComment(CommentDTO commentDTO) {
        Optional<NewsEntity> newsEntityOptional = newsRepository.findById(commentDTO.getNewsId());
        Optional<UserEntity> authorEntityOptional = userRepository.findById(commentDTO.getAuthor().getUserId());

        if (newsEntityOptional.isPresent() && authorEntityOptional.isPresent()) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setNews(newsEntityOptional.get());
            commentEntity.setAuthor(authorEntityOptional.get());
            commentEntity.setText(commentDTO.getText());
            commentRepository.save(commentEntity);
        }
    }

}
