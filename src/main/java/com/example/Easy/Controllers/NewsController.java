package com.example.Easy.Controllers;

import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Models.UserDTO;
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
        return newsService.getNewsByCategoryName(category,pageNumber,pageSize,sortBy);
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
    public ResponseEntity<?> postNews(@RequestBody NewsDTO newsDTO) {
        newsService.postNews(newsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{newsId}")
    public ResponseEntity<?> deletePostById(@PathVariable UUID newsUUID) {
        newsService.deletePostById(newsUUID);
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

    @PostMapping("like/{newsId}")
    public ResponseEntity<?> likePost(@PathVariable UUID newsId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(newsService.likePost(newsId, userDTO));
    }

    @DeleteMapping("like/{newsId}")
    public ResponseEntity<?> unlikePost(@PathVariable UUID newsId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(newsService.unlikePost(newsId, userDTO));
    }

    @GetMapping("like/{newsId}")
    public int getLikes(
            @PathVariable UUID newsId,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        return newsService.getLikes(newsId, pageNumber, pageSize, sortBy);
    }

    @PostMapping("bookmark/{newsId}")
    public ResponseEntity<?> bookmark(@PathVariable UUID newsId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(newsService.bookmark(newsId, userDTO));
    }

    @DeleteMapping("bookmark/{newsId}")
    public ResponseEntity<?> removeBookmark(@PathVariable UUID newsId, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(newsService.removeBookmark(newsId, userDTO));
    }
    @GetMapping("bookmark/{newsId}")
    public int getBookmarks(@PathVariable("newsId") UUID newsId,
                            @RequestParam(required = false) Integer pageNumber,
                            @RequestParam(required = false) Integer pageSize,
                            @RequestParam(required = false) String sortBy){
        return newsService.getBookmarks(newsId,pageNumber,pageSize,sortBy);
    }

}
