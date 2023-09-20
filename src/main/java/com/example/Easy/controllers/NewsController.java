package com.example.Easy.controllers;

import com.example.Easy.models.response.CommentViewResponse;
import com.example.Easy.models.response.NewsInteractionsResponse;
import com.example.Easy.models.response.NewsResponse;
import com.example.Easy.requests.CreateInteractionRequest;
import com.example.Easy.requests.CreateNewsRequest;
import com.example.Easy.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public Page<NewsResponse> getAllNews(@RequestParam(required = false) Integer pageNumber,
                                         @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) String sortBy,
                                         @RequestParam(required = false) String category,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) String author) {
        return newsService.getAllNews(pageNumber, pageSize, sortBy, category,title,author)
                .map(x -> new NewsResponse(x, newsService.getInteractions(x.getNewsId())));
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsResponse> getNews(@PathVariable("newsId") UUID newsId,
                                                @RequestParam(required = false) Integer pageNumber,
                                                @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(required = false) String sortBy) {
        return new ResponseEntity<>(new NewsResponse(newsService.getNewsById(newsId), newsService.getInteractions(newsId)), HttpStatus.OK);
    }
    @PatchMapping("/{newsId}")
    public ResponseEntity<NewsResponse> patchNews(@PathVariable("newsId") UUID newsId,@ModelAttribute CreateNewsRequest createNewsRequest) {
        return new ResponseEntity<>(new NewsResponse(newsService.patchNews(newsId,createNewsRequest)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<NewsResponse> postNews(@ModelAttribute CreateNewsRequest createNewsRequest) {
        return new ResponseEntity(new NewsResponse(newsService.postNews(createNewsRequest)), HttpStatus.CREATED);
    }

    @DeleteMapping("{newsId}")
    public ResponseEntity<NewsResponse> deletePostById(@PathVariable("newsId") UUID newsUUID,
                                                       @RequestHeader(name = "Accept-Language", required = false) final Locale locale) {
        return new ResponseEntity(new NewsResponse(newsService.deletePostById(newsUUID)), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{newsId}/comments")
    public ResponseEntity<Page<CommentViewResponse>> getComments(@PathVariable("newsId") UUID newsId,
                                                                 @RequestParam(required = false) Integer pageNumber,
                                                                 @RequestParam(required = false) Integer pageSize,
                                                                 @RequestParam(required = false) String sortBy) {
        return new ResponseEntity<>(newsService.getComments(newsId, pageNumber, pageSize, sortBy).map(CommentViewResponse::new), HttpStatus.OK);
    }

    @GetMapping("/recommendations") //make a generic recommendation too
    public Page<NewsResponse> getRecommendedNews(@RequestParam(required = false) UUID userId,
                                                 @RequestParam(required = false) Integer pageNumber,
                                                 @RequestParam(required = false) Integer pageSize,
                                                 @RequestParam(required = false) String sortBy,
                                                 @RequestHeader(name = "Accept-Language", required = false) final Locale locale) {
        return newsService.getRecommendedNews(userId, pageNumber, pageSize, sortBy).map(NewsResponse::new);
    }

    @PostMapping("/interactions")//add like etc,maybe should be in user?
    public ResponseEntity<NewsInteractionsResponse> patchInteractions(@RequestBody CreateInteractionRequest createInteractionRequest) {
        return new ResponseEntity<>(new NewsInteractionsResponse(createInteractionRequest.getNewsId(), newsService.patchInteractions(createInteractionRequest)), HttpStatus.OK);
    }


}
