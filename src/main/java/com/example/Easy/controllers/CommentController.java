package com.example.Easy.controllers;

import com.example.Easy.models.CommentDTO;
import com.example.Easy.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody CommentDTO commentDTO){
        commentService.post(commentDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{commentId}")
    public CommentDTO get(@PathVariable("commentId") UUID commentId){
        return commentService.get(commentId);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable("commentId")UUID commentId){
        commentService.delete(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/{commentId}")
    public CommentDTO patch(@PathVariable("commentId")UUID commentId,@RequestBody CommentDTO commentDTO){
        return commentService.patch(commentId,commentDTO);
    }
}
