package com.example.Easy.Controllers;

import com.example.Easy.Models.CommentDTO;
import com.example.Easy.Services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
