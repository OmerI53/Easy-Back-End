package com.example.Easy.repository.dao;

import com.example.Easy.mappers.CommentMapper;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CommentDao implements Dao<CommentDTO>{

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ResourceBundleMessageSource source;

    @Override
    public CommentDTO get(UUID id) {
        return commentMapper.toCommentDTO(commentRepository.findById(id)
                .orElseThrow(()-> new NullPointerException(source.getMessage("comment.notfound",null, LocaleContextHolder.getLocale()))));
    }

    @Override
    public List<CommentDTO> getAll() {
        return null;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        return commentMapper.toCommentDTO(commentRepository.save(commentMapper.toCommentEntity(commentDTO)));
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) {
        return commentMapper.toCommentDTO(commentRepository.save(commentMapper.toCommentEntity(commentDTO)));
    }

    @Override
    public CommentDTO delete(CommentDTO commentDTO) {
        commentRepository.delete(commentMapper.toCommentEntity(commentDTO));
        return commentDTO;
    }
}
