package com.example.WhatTheTekBlog.controllers;

import com.auth0.jwt.JWT;
import com.example.WhatTheTekBlog.models.Comments;
import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.models.Tags;
import com.example.WhatTheTekBlog.models.User;
import com.example.WhatTheTekBlog.repositories.CommentsRepository;
import com.example.WhatTheTekBlog.services.CommentsService;
import com.example.WhatTheTekBlog.services.PostService;
import com.example.WhatTheTekBlog.services.TagsService;
import com.example.WhatTheTekBlog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@RestController
public class CommentsController {

//ResourceNotFoundExcepion

    private CommentsService commentsService;
    private UserService userService;
    private PostService postService;


    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping("/comment/create/{token}")
    public ResponseEntity<Comments> create(@RequestBody Comments comments, @PathVariable String token) {
            return new ResponseEntity<>(commentsService.create(token, comments), HttpStatus.CREATED);
        }

    @GetMapping("/comments")
    public ResponseEntity<Iterable<Comments>> findAll() {
        return new ResponseEntity<>(this.commentsService.findAllComments(), HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Optional<Comments>> getCommentById(@PathVariable("id") Long id) {

        return new ResponseEntity<>(this.commentsService.findCommentById(id), HttpStatus.OK);
    }

    @GetMapping("/comments/post/{post_id}")
    public ResponseEntity<List<Comments>> getCommentsByPostId(@PathVariable("post_id") Long post_id) {

        return new ResponseEntity<>(this.commentsService.findAllCommentByPost(post_id), HttpStatus.OK);

    }


    @PutMapping("/comment/update/{id}")
    public ResponseEntity<Comments> updateComments(@PathVariable("id") Long commentId, @RequestBody Comments comments) {
        return new ResponseEntity<>(this.commentsService.updateComments(commentId, comments), HttpStatus.OK);
    }

    @DeleteMapping("/comment/delete/{comment_id}")
    public ResponseEntity<Boolean> deleteComments(@PathVariable ("comment_id") Long commentId) {
        return new ResponseEntity<>(this.commentsService.delete(commentId), HttpStatus.OK);
    }

}
