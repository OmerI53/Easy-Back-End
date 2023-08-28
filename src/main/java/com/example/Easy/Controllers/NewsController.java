package com.example.Easy.Controllers;

import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Services.NewsService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/News")
public class NewsController {


    @Autowired
    NewsService newsService;

    @GetMapping("/getNews")
    public ResponseEntity addNews(@PathVariable NewsDTO newsDTO){
       newsService.addNews(newsDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/deleteNews")
    public ResponseEntity deleteNews(@PathVariable NewsDTO newsDTO){
        newsService.deleteNews(newsDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
