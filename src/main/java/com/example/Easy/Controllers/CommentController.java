package com.example.Easy.Controllers;

import com.example.Easy.Models.CommentDTO;
import com.example.Easy.Services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity postComment(@RequestBody CommentDTO commentDTO){
        commentService.postComment(commentDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/{commentId}")
    public CommentDTO getCommentById(@PathVariable("commentId") UUID commentId){
        return commentService.getCommentById(commentId);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteCommentById(@PathVariable("commentId")UUID commentId){
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{commentId}")
    public CommentDTO patchCommentById(@PathVariable("commentId")UUID commentId,@RequestBody CommentDTO commentDTO){
        return commentService.patchCommentById(commentId,commentDTO);
    }
}
