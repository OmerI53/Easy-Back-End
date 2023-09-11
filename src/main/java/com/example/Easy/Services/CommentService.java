package com.example.Easy.Services;

import com.example.Easy.Entities.CommentEntity;
import com.example.Easy.Mappers.CommentMapper;
import com.example.Easy.Models.CommentDTO;
import com.example.Easy.Repository.CommentRepository;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

    private  CommentRepository commentRepository;
    private  CommentMapper commentMapper;
    private  UserRepository userRepository;
    private  NewsRepository newsRepository;



}
