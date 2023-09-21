package com.example.Easy.services;

import com.example.Easy.models.CommentDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.dao.CommentDao;
import com.example.Easy.requests.CreateCommentRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    private final UserService userService;
    private final NewsService newsService;

    @Transactional
    public CommentDTO postComment(CreateCommentRequest createCommentRequest) {
        UserDTO user = userService.getUser(createCommentRequest.getUserId());
        NewsDTO news = newsService.getNewsById(createCommentRequest.getNewsId());
        CommentDTO comment = CommentDTO.builder()
                .news(news)
                .author(user)
                .text(createCommentRequest.getText())
                .creationTime(LocalDateTime.now())
                .build();
        return commentDao.save(comment);
    }
    @Transactional
    public CommentDTO getComment(UUID commentId) {
        return commentDao.get(commentId);
    }
    @Transactional
    public CommentDTO deleteComment(UUID commentId) {
        CommentDTO commentDTO = getComment(commentId);
        return commentDao.delete(commentDTO);
    }
    @Transactional
    public CommentDTO patchComment(UUID commentId,CreateCommentRequest createCommentRequest) {
        CommentDTO comment = getComment(commentId);
        if(createCommentRequest.getText()!=null || !createCommentRequest.getText().equals(""))
            comment.setText(createCommentRequest.getText());
        return commentDao.update(comment);
    }
}
