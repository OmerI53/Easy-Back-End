package com.example.Easy.Services;

import com.example.Easy.Mappers.NewsMapper;
import com.example.Easy.Mappers.UserMapper;
import com.example.Easy.Models.CommentDTO;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Models.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    NewsService newsService;
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;


}