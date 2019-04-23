package com.example.WhatTheTekBlog.controllers;

import com.example.WhatTheTekBlog.config.SecurityConfig;
import com.example.WhatTheTekBlog.models.Comments;
import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.models.Tags;
import com.example.WhatTheTekBlog.models.User;
import com.example.WhatTheTekBlog.repositories.PostRepository;
import com.example.WhatTheTekBlog.services.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc//(secure = false)
//@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@RunWith(SpringRunner.class)
public class PostControllerTest {

    private Long givenId = 1L;
    private Set<Comments> commentsList = new HashSet<>();
    private Set<Tags> tagsSet = new HashSet<>();
    private Post post = new Post();

    @Before
    public void setup() {

        Comments comments = new Comments();
        Tags tags = new Tags();
        tags.setTagName("tag1");
        comments.setComments("comment1");
        User author1 = new User();
        author1.setId(1);
        author1.setName("author1");

        post.setPostID(givenId);
        post.setPostContent("Post1Content");
        post.setPostTitle("Post1Title");
        post.setPostSummary("Post1Summary");
        post.setCreator(author1);
        post.setCreatedDate(null);
        comments.setUser(author1);
        comments.setPost(post);
        tags.setId(1);
        post.setComments(commentsList);
        post.setTagsSet(tagsSet);
        post.setPostID(givenId);
    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostRepository repository;
    @MockBean
    private PostService service;

    @Test
    public void testGetPost() throws Exception {
        BDDMockito
                .given(service.findByPostId(givenId))
                .willReturn(Optional.of(post));

        String expectedContent = "{\"postID\":1,\"postTitle\":\"Post1Title\",\"postSummary\":\"Post1Summary\"," +
                "\"postContent\":\"Post1Content\",\"createdDate\":null,\"creator\":{\"id\":1,\"name\":\"author1\"},\"myFile\":null}";

        this.mvc.perform(MockMvcRequestBuilders
                .get("/post/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testCreatePost() throws Exception {
        BDDMockito
                .given(service.createPost(post))
                .willReturn(post);

        String expectedContent = "{\"postID\":1,\"postTitle\":\"Post1Title\",\"postSummary\":\"Post1Summary\"," +
                "\"postContent\":\"Post1Content\",\"createdDate\":null,\"creator\":{\"id\":1,\"name\":\"author1\"},\"myFile\":null}";

        this.mvc.perform(MockMvcRequestBuilders
                .post("/users/createPost/eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlFUUXdNRFk1TXpFek5qazFOREUwTnpFNE5FSXpNREl6UlVZMU5UUTBOamRDTWpZNE5qQTBOdyJ9.eyJuaWNrbmFtZSI6InRlc3RpbmcxMjM1IiwibmFtZSI6ImRpdGljQG1haWxsaW5rLmxpdmUiLCJwaWN0dXJlIjoiaHR0cHM6Ly9zLmdyYXZhdGFyLmNvbS9hdmF0YXIvMGVmMWNkMjYxNjc1MzBmMDIwM2ExMTBiYzU3Yjg4YTc_cz00ODAmcj1wZyZkPWh0dHBzJTNBJTJGJTJGY2RuLmF1dGgwLmNvbSUyRmF2YXRhcnMlMkZkaS5wbmciLCJ1cGRhdGVkX2F0IjoiMjAxOS0wNC0xN1QxNjoxMDo0OC41MjJaIiwiaXNzIjoiaHR0cHM6Ly93aGF0dGhldGVrLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw1Y2I3NTAwODcyNDI5YjExNDgwMGY2YTIiLCJhdWQiOiJ2Nk9NaE5tTjBPTzNhUFFuQzlWbkVBQ0JEWDdDT1IwTiIsImlhdCI6MTU1NTUyMjI3NiwiZXhwIjoxNTU1NTU4Mjc2LCJhdF9oYXNoIjoiYVFyTzB3WFJtS3JSVmZnSjE1VTZfQSIsIm5vbmNlIjoiLUlpRklnLWlydWRqNUtzRjltMGhrblZ2cFhQRGNSY1IifQ.Eq63Ta9VNUS9ITonZu-U9SaAkINeWawyGgg2rxlaPUPEtPHAjC2egaGzdZJAUXVmsySK2Is_0u9KvA-XQDuSv5rVxhxXQzn2jqePqzb_rIBUXALEtPTKiGGWsjX2_qiay_1x63K1nAWWSM0eVR0Fg67L01VCmRUAIoVZvPCmQHAccA71lvsDttvU0vFgBJ8NCJxBoG4ZQsRRdgHuBx_BmFH20B_hPp5WdWmQkl74UOL2aJaDn2VsHem4ZzGFJnjC3WlqxOamPkX9IVyKpcRvFb9VFEQxM5j2_nBrkyG5OLX20zEJlahEfU5RXeGUsq-nJFziJvQ52ZR2PI6MHWJ55w")
                .content(expectedContent)
                .header("authorization", "Bearer " + SecurityConfig.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testUpdatePost() throws Exception {
        BDDMockito
                .given(service.findByPostId(givenId))
                .willReturn(Optional.of(post));

        String expectedContent = "{\"postID\":1,\"postTitle\":\"Post1Title\",\"postSummary\":\"Post1Summary\"," +
                "\"postContent\":\"Post1Content\",\"createdDate\":null,\"creator\":{\"id\":1,\"name\":\"author1\"},\"myFile\":null}";

        this.mvc.perform(MockMvcRequestBuilders
                .put("/users/updatePost/" + givenId)
                .content(expectedContent)
                .header("authorization", "Bearer " + SecurityConfig.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeletePost() throws Exception {
        BDDMockito
                .given(service.findByPostId(givenId))
                .willReturn(Optional.of(post));

        String expectedContent = "{\"postID\":1,\"postTitle\":\"Post1Title\",\"postSummary\":\"Post1Summary\"," +
                "\"postContent\":\"Post1Content\",\"createdDate\":null,\"creator\":{\"id\":1,\"name\":\"author1\"},\"myFile\":null}";
        this.mvc.perform(MockMvcRequestBuilders
                .delete("/users/deletePost/" + givenId)
                .header("Authorization", "Bearer " + SecurityConfig.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //    .andExpect(MockMvcResultMatchers.status());
    }

    @Test
    public void testGetTags() throws Exception {
        BDDMockito
                .given(service.findByPostId(givenId))
                .willReturn(Optional.of(post));

        String expectedContent = "{\"postID\":1,\"postTitle\":\"Post1Title\",\"postSummary\":\"Post1Summary\"," +
                "\"postContent\":\"Post1Content\",\"createdDate\":null,\"creator\":{\"id\":1,\"name\":\"author1\"},\"myFile\":null}";

        this.mvc.perform(MockMvcRequestBuilders
                .get("/post/tags/" + givenId)
                .content(expectedContent)
                .header("authorization", "Bearer " + SecurityConfig.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
