package com.example.Easy.Controllers;

import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public Page<NewsDTO> getAllNews(@RequestParam(required = false) Integer pageNumber,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String sortBy){
        return newsService.getAllNews(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/category/{categoryId}")
    public Page<NewsDTO> getNewsByCategory(@PathVariable("categoryId") String category,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String sortBy){
        return newsService.getNewsByCategoryId(category,pageNumber,pageSize,sortBy);
    }


    @GetMapping("/title/{title}")
    public Page<NewsDTO> getNewsByTitle(@PathVariable("title") String title,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String sortBy){
        return newsService.getNewsByTitle(title,pageNumber,pageSize,sortBy);
    }
    @PostMapping("/image")
    public String postImages(@RequestBody MultipartFile file) throws IOException {
        return newsService.uploadImage(file);
    }
    @PostMapping
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
