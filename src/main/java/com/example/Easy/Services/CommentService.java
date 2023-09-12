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
