package com.example.Easy.services;

import com.example.Easy.mappers.NewsMapper;
import com.example.Easy.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    @Test
    void commentPostTest() {
    }
}