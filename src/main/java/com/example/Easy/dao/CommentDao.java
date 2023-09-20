package com.example.Easy.dao;

import com.example.Easy.models.CommentDTO;

import java.util.UUID;

public interface CommentDao {
    public void post(CommentDTO commentDTO);
    public CommentDTO get(UUID commentId);
    public void delete(UUID commentId);
    public CommentDTO patch(UUID commentId,CommentDTO commentDTO);

}
