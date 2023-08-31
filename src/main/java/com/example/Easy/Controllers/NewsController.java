package com.example.Easy.Controllers;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    NewsService newsService;

    @GetMapping
    public Page<NewsDTO> getAllNews(@RequestParam(required = false) Integer pageNumber,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String sortBy){
        return newsService.getAllNews(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/category/{categoryId}")
    public Page<NewsDTO> getNewsByCategory(@PathVariable("categoryId") Long categoryId,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String sortBy){
        return newsService.getNewsByCategoryId(categoryId,pageNumber,pageSize,sortBy);
    }


    @GetMapping("/title/{title}")
    public Page<NewsDTO> getNewsByTitle(@PathVariable("title") String title,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String sortBy){
        return newsService.getNewsByTitle(title,pageNumber,pageSize,sortBy);
    }
    @PostMapping()
    public ResponseEntity postNews(@RequestBody NewsDTO newsDTO){
        newsService.postNews(newsDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{newsUUID}")
    public ResponseEntity deletePostById(@PathVariable("newsUUID")UUID newsUUID){
        newsService.deletePostById(newsUUID);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }






}
