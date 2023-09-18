package com.example.Easy.controllers;

import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.services.NewsService;
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

    @GetMapping("/all")
    public Page<NewsDTO> getAllNews(@RequestParam(required = false) Integer pageNumber,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String sortBy){
        return newsService.get(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/category/{categoryId}")
    public Page<NewsDTO> getNewsByCategory(@PathVariable("categoryId") String category,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer pageSize,
                                           @RequestParam(required = false) String sortBy){
        return newsService.get(category,pageNumber,pageSize,sortBy);
    }

    @GetMapping("/title/{title}")
    public Page<NewsDTO> getNewsByTitle(@PathVariable("title") String title,
                                        @RequestParam(required = false) Integer pageNumber,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) String sortBy){
        return newsService.getByTitle(title,pageNumber,pageSize,sortBy);
    }

    @PostMapping("/image")
    public String postImages(@RequestBody MultipartFile file) throws IOException {
        return newsService.uploadImage(file);
    }

    @PostMapping("/post")
    public ResponseEntity<Void> postNews(@RequestBody NewsDTO newsDTO) {
        newsService.post(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{newsId}")
    public ResponseEntity<Void> deletePostById(@PathVariable UUID newsUUID) {
        newsService.delete(newsUUID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recommendations/{userId}")
    public Page<NewsDTO> getRecommendedNews(@PathVariable("userId") UUID userId,
                                            @RequestParam(required = false) Integer pageNumber,
                                            @RequestParam(required = false) Integer pageSize,
                                            @RequestParam(required = false) String sortBy){
        return newsService.getRecommendedNews(userId,pageNumber,pageSize,sortBy);
    }
    @GetMapping("/following/{userId}")
    public Page<NewsDTO> getFollowingNews(@PathVariable("userId") UUID userId,
                                          @RequestParam(required = false) Integer pageNumber,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String sortBy){
        return newsService.getFollowingNews(userId,pageNumber,pageSize,sortBy);
    }

    @PostMapping("/like/{newsId}")
    public ResponseEntity<String> likePost(@PathVariable UUID newsId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(newsService.likePost(newsId, userDTO));
    }

    @DeleteMapping("/like/{newsId}")
    public ResponseEntity<String> unlikePost(@PathVariable UUID newsId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(newsService.unlikePost(newsId, userDTO));
    }

    @GetMapping("/like/{newsId}")
    public int getLikes(
            @PathVariable UUID newsId,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        return newsService.getLikes(newsId, pageNumber, pageSize, sortBy);
    }


    @PostMapping("/bookmark/{newsId}")
    public ResponseEntity<String> bookmark(@PathVariable UUID newsId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(newsService.bookmark(newsId, userDTO));
    }

    @DeleteMapping("/bookmark/{newsId}")
    public ResponseEntity<String> removeBookmark(@PathVariable UUID newsId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(newsService.removeBookmark(newsId, userDTO));
    }
    @GetMapping("/bookmark/{newsId}")
    public int getBookmarks(@PathVariable("newsId") UUID newsId,
                            @RequestParam(required = false) Integer pageNumber,
                            @RequestParam(required = false) Integer pageSize,
                            @RequestParam(required = false) String sortBy){
        return newsService.getBookmarks(newsId,pageNumber,pageSize,sortBy);
    }

}
