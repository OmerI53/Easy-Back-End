package com.example.Easy.services;

import com.example.Easy.mappers.CommentMapper;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.CommentRepository;
import com.example.Easy.requests.CreateCommentRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final NewsService newsService;
    private final  ResourceBundleMessageSource source;

    @Transactional
    public CommentDTO postComment(CreateCommentRequest createCommentRequest) {
        UserDTO user = userService.getUserById(createCommentRequest.getUserId());
        NewsDTO news = newsService.getNewsById(createCommentRequest.getNewsId());
        CommentDTO comment = CommentDTO.builder()
                .news(news)
                .author(user)
                .text(createCommentRequest.getText())
                .creationTime(LocalDateTime.now())
                .build();
        commentRepository.save(commentMapper.toCommentEntity(comment));
        return comment;
    }
    @Transactional
    public CommentDTO getComment(UUID commentId) {
        return commentMapper.toCommentDTO(commentRepository.findById(commentId)
                .orElseThrow(()-> new NullPointerException(source.getMessage("comment.notfound",null, LocaleContextHolder.getLocale()))));
    }
    @Transactional
    public CommentDTO deleteComment(UUID commentId) {
        CommentDTO commentDTO = getComment(commentId);
        commentRepository.delete(commentMapper.toCommentEntity(commentDTO));
        return commentDTO;
    }
    @Transactional
    public CommentDTO patchComment(UUID commentId,CreateCommentRequest createCommentRequest) {
        CommentDTO comment = getComment(commentId);
        if(createCommentRequest.getText()!=null || !createCommentRequest.getText().equals(""))
            comment.setText(createCommentRequest.getText());
        commentRepository.save(commentMapper.toCommentEntity(comment));
        return comment;
    }
}
