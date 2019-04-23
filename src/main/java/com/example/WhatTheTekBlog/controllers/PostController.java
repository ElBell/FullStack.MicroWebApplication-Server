package com.example.WhatTheTekBlog.controllers;

import com.auth0.jwt.JWT;
import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.models.Tags;
import com.example.WhatTheTekBlog.models.User;
import com.example.WhatTheTekBlog.services.PostService;
import com.example.WhatTheTekBlog.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping
public class PostController {

    private final Logger LOG = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    public PostController(PostService service) {
        this.postService = service;
    }

    @GetMapping("/post")
    public ResponseEntity<Iterable<Post>> findAll() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Long postId) {
        Optional<Post> post = postService.findByPostId(postId);
        return post.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/users/createPost/{token}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable String token) {
        System.out.println(post);
        String name = JWT.decode(token).getClaim("nickname").asString();
        if (userService.contains(name)) {
            LOG.info("Creating a new Post: {}", post);
            User user = userService.findByName(name);
            post.setCreator(user);
            return new ResponseEntity<>(this.postService.createPost(post), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/post/tags/{postId}")
    public ResponseEntity<Set<Tags>> getTags(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.getTags(postId), HttpStatus.OK);
    }

    @PutMapping("/users/updatePost/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        Optional<Post> currentPost = this.postService.findByPostId(postId);

        if (currentPost == null) {
            LOG.info("Unable to update, Post not found with Id: {}", postId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOG.info("Updating Post: {}", postId);
        return new ResponseEntity<>(this.postService.updatePost(postId, post), HttpStatus.OK);
    }

    @DeleteMapping("/users/deletePost/{postId}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long postId) {
        return new ResponseEntity<>(this.postService.delete(postId), HttpStatus.OK);
    }

}
