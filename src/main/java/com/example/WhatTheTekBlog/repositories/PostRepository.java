package com.example.WhatTheTekBlog.repositories;

import com.example.WhatTheTekBlog.models.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  //Page<Post> findAllPages(Pageable pageable);
 //   Post findByPostID(Long id);
}
