package com.example.Easy.controllers;

import com.example.Easy.models.response.CommentResponse;
import com.example.Easy.requests.CreateCommentRequest;
import com.example.Easy.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> postComment(@RequestBody CreateCommentRequest createCommentRequest){ //
        return new ResponseEntity<>(new CommentResponse(commentService.postComment(createCommentRequest)),HttpStatus.CREATED);
    }
    @GetMapping("{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@RequestHeader(name = "Accept-Language", required = false) final Locale locale,
                                                          @PathVariable("commentId") UUID commentId){
        return new ResponseEntity<>(new CommentResponse(commentService.getComment(commentId)), HttpStatus.OK);
    }
    @DeleteMapping("{commentId}")
    public ResponseEntity<CommentResponse> deleteCommentById(@PathVariable("commentId")UUID commentId){
        return new ResponseEntity<>(new CommentResponse(commentService.deleteComment(commentId)),HttpStatus.OK);
    }
    @PatchMapping("{commentId}")
    public ResponseEntity<CommentResponse> patchCommentById(@PathVariable("commentId")UUID commentId,@RequestBody CreateCommentRequest createCommentRequest){
        return new ResponseEntity<>(new CommentResponse(commentService.patchComment(commentId,createCommentRequest)),HttpStatus.OK);
    }
}
