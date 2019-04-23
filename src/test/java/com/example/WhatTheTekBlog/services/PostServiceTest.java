package com.example.WhatTheTekBlog.services;

import com.example.WhatTheTekBlog.WhatTheTekBlogApplication;
import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.repositories.PostRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WhatTheTekBlogApplication.class)
public class PostServiceTest {

    @MockBean
    private PostService postService;
    @MockBean
    private PostRepository mockPostRepo;

    @Before
    public void setup() {
        mockPostRepo = Mockito.mock(PostRepository.class);
        postService = new PostService(mockPostRepo, null);
    }

    @Test
    public void createPost() {
        //Given
        Long givenId = 1L;
        Post post = new Post();
        post.setPostID(givenId);
        Post expected = new Post();
        expected.setPostID(givenId);
        //When
        Mockito.when(mockPostRepo.save(post)).thenReturn(expected);
        //Then
        Post actual = postService.createPost(post);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        //Given
        Long givenId1 = 1L;
        Long givenId2 = 2L;
        Post post1 = new Post();
        post1.setPostID(givenId1);
        Post post2 = new Post();
        post2.setPostID(givenId2);
        mockPostRepo.save(post1);
        mockPostRepo.save(post2);
        List<Post> expected = new ArrayList<>();
        expected.add(post1);
        expected.add(post2);
        //When
        Mockito.when(mockPostRepo.findAll()).thenReturn(expected);
        List<Post> actual = (List<Post>) postService.findAll();
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFindByPostId() {
        //Given
        Long givenId = 1L;
        Post expected = new Post();
        expected.setPostID(givenId);
        mockPostRepo.save(expected);
        //When
        Mockito.when(mockPostRepo.findById(givenId)).thenReturn(java.util.Optional.of(expected));
        Optional<Post> actual = postService.findByPostId(givenId);
        //Then
        assertEquals(Optional.of(expected), actual);
    }

    @Test
    public void testUpdate() {
        //Given
        Post post1 = new Post();
        Long givenId = 1000L;
        post1.setPostID(givenId);
        post1.setPostTitle("Post1Title");
        Post expected = new Post();
        expected.setPostID(givenId);
        expected.setPostTitle("Post1TitleUpdated");
        mockPostRepo.save(post1);
        //When
        Mockito.when(mockPostRepo.findById(givenId)).thenReturn(Optional.of(post1));
        postService.updatePost(givenId, expected);
        String actual = postService.findByPostId(givenId).get().getPostTitle();
        //Then
        Assert.assertEquals(post1.getPostTitle(), actual);
    }

    @Test
    public void testDelete() {
        //Given
        Long givenId1 = 1000L;
        Long givenId2 = 2000L;
        Post post1 = new Post();
        post1.setPostID(givenId1);
        Post post2 = new Post();
        post2.setPostID(givenId2);
        mockPostRepo.save(post1);
        mockPostRepo.save(post2);

        List<Post> expected = new ArrayList<>();
        expected.add(post1);
        expected.add(post2);

        Mockito.when(mockPostRepo.findAll()).thenReturn(expected);

        //When
        postService.delete(givenId1);
        //Then
        List<Post> actual = mockPostRepo.findAll();
        Assert.assertEquals(actual,expected);
    }
}
