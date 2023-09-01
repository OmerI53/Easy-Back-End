package com.example.Easy.Controllers;

import com.example.Easy.Services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/news")
public class NewsController {

    @Autowired
    NewsService newsService;
}
