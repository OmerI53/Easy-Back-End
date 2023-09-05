package com.example.Easy.Services;

import com.example.Easy.Mappers.NewsMapper;
import com.example.Easy.Mappers.UserMapper;
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