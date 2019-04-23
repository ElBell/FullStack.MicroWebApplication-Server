package com.example.WhatTheTekBlog.services;

import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.models.Tags;
import com.example.WhatTheTekBlog.repositories.PostRepository;
import com.example.WhatTheTekBlog.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {

    private PostRepository postRepository;
    private TagsRepository tagsRepository;

    @Autowired
    public PostService(PostRepository repository, TagsRepository tagsRepository) {
        this.postRepository = repository;
        this.tagsRepository = tagsRepository;
    }

    public Post createPost(Post post) {
        Post savedPost = this.postRepository.save(post);
        savedPost.setTagsSet(saveTags(post));
        return savedPost;
    }

    public Iterable<Post> findAll() {
        return this.postRepository.findAll();
    }

    public Optional<Post> findByPostId(Long postId) {
        return this.postRepository.findById(postId);
    }

    public Post updatePost(Long postId, Post post) {
        Post originalPost = postRepository.findById(postId).get();
        originalPost.setPostTitle(post.getPostTitle());
        originalPost.setPostSummary(post.getPostSummary());
        originalPost.setPostContent(post.getPostContent());
        originalPost.setMyFile(post.getMyFile());
        removeTags(originalPost);
        originalPost.setTagsSet(saveTags(post));
        postRepository.deleteById(originalPost.getPostID());
        return postRepository.save(originalPost);
    }

    public Boolean delete(Long postId) {
        postRepository.deleteById(postId);
        return true;
    }

    public Set<Tags> getTags(Long postId) {
        return postRepository.existsById(postId) ? new HashSet<>(postRepository.findById(postId).get().getTagsSet()) : null;
    }


    private Set<Tags> saveTags(Post savedPost) {
        Set<Tags> actualTags = new HashSet<>();
        savedPost.getTagsSet().forEach(tags -> actualTags.add(tagsRepository.findByTagName(tags.getTagName())));
        for (Tags tags : actualTags) {
            Tags updatedTag = tagsRepository.findByTagName(tags.getTagName());
            updatedTag.addPost(savedPost);
            tagsRepository.save(updatedTag);
        }
        return actualTags;
    }

    private void removeTags(Post originalPost) {
        for (Tags tags : originalPost.getTagsSet()) {
            Tags updatedTag = tagsRepository.findByTagName(tags.getTagName());
            updatedTag.removePost(originalPost);
            tagsRepository.save(updatedTag);
        }
    }
}
