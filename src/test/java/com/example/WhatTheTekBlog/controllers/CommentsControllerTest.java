package com.example.WhatTheTekBlog.controllers;

import com.example.WhatTheTekBlog.models.Comments;
import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.models.Tags;
import com.example.WhatTheTekBlog.models.User;
import com.example.WhatTheTekBlog.repositories.CommentsRepository;
import com.example.WhatTheTekBlog.repositories.PostRepository;
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

import java.text.ParseException;
import java.util.*;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CommentsControllerTest {

    private Long commentId = 30L;
    private String commentString = "This is a comment";
    private Comments comments1 = new Comments();
    private String token;

    public CommentsControllerTest() throws ParseException {
    }

    @Before
    public void setup() {
        User user = new User();
        user.setId(10);
        user.setName("user1");
        Post post = new Post();
        post.setCreator(user);
        comments1.setCommentId(commentId);
        comments1.setComments(commentString);
        comments1.setCreatedDate(null);
        comments1.setUser(user);
        comments1.setPost(post);

    }

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentsRepository repo;

    @Test
    public void testGetComments() throws Exception {
        BDDMockito
                .given(repo.findById(commentId))
                .willReturn(Optional.of(comments1));

        String expectedContent = "{\"commentId\":30,\"comments\":\"This is a comment\",\"user\":{\"id\":10,\"name\":\"user1\"}," +
                "\"post\":{\"postID\":null,\"postTitle\":null,\"postSummary\":null,\"postContent\":null,\"createdDate\":\"2019-04-23\"," +
                "\"creator\":{\"id\":10,\"name\":\"user1\"},\"myFile\":null},\"createdDate\":null}";

        this.mvc.perform(MockMvcRequestBuilders
                .get("/comment/" + commentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testCreateComment() throws Exception {
        BDDMockito
                .given(repo.save(comments1))
                .willReturn(comments1);

        String expectedContent = "{\"commentId\":31,\"comments\":\"This is a comment\",\"createdDate\":null,\"user\":null}";

        this.mvc.perform(MockMvcRequestBuilders
                .post("/comment/create/eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlFUUXdNRFk1TXpFek5qazFOREUwTnpFNE5FSXpNREl6UlVZMU5UUTBOamRDTWpZNE5qQTBOdyJ9.eyJuaWNrbmFtZSI6ImVsZW9ub3JiYXJ0IiwibmFtZSI6ImVsZW9ub3JiYXJ0QGdtYWlsLmNvbSIsInBpY3R1cmUiOiJodHRwczovL3MuZ3JhdmF0YXIuY29tL2F2YXRhci81MDk2MjczNDM3ZThjOGFjOTIwMTAwYTE5NDM1YzY2Yz9zPTQ4MCZyPXBnJmQ9aHR0cHMlM0ElMkYlMkZjZG4uYXV0aDAuY29tJTJGYXZhdGFycyUyRmVsLnBuZyIsInVwZGF0ZWRfYXQiOiIyMDE5LTA0LTE0VDAzOjM5OjQyLjQ0NFoiLCJpc3MiOiJodHRwczovL3doYXR0aGV0ZWsuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDVjYjJhYjFkM2QzNmU0MTBlMWIzMDRhNiIsImF1ZCI6InY2T01oTm1OME9PM2FQUW5DOVZuRUFDQkRYN0NPUjBOIiwiaWF0IjoxNTU1MzY1NTI3LCJleHAiOjE1NTU0MDE1MjcsImF0X2hhc2giOiJHNzdsa1Q5cXE5TDh5NnFMUFo5SXJBIiwibm9uY2UiOiJZNmxaUmdUd0RtMGE0ejV4UUJLd2hhZkE5eDdCNjJScCJ9.h_FYlFmckr4lpQD-fc7Uh8y2Omal474qm1G3x4egTnCVSj35pOlgMXyI30IIgQN2A1-zXWAi9SDrC4CrofK1ahiNB1Ac3aIs0EBoO91TVcKJe03TKI2QE2zcIa46grXJld84DjNPgZkYy-HJGBYc-vScagO3YlJ_bX8TGEUoyt-BIUeobIfyauqOzyLomYpDP1soIeuvg_Fe8_Mk2iCVXlxSNJHaYEfC9SGg2m7XqolBhzNNC3eSCfqK8OFPm4v7n_fqojMJ9NXlE73S-dxM8ybE_08xCp0X3jJOY2S4mywGijprX0R9gMUlcbLoBRYdH0oRHA5k7jeoIqk3BFrpog")
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    public void testUpdateComment() throws Exception {
//        BDDMockito
//                .given(repo.findById(commentId))
//                .willReturn(Optional.of(comments1));
//
//        String expectedContent = "{\"commentId\":30,\"comments\":\"This is a new comment\",\"createdDate\":null,\"user\":null}";
//        this.mvc.perform(MockMvcRequestBuilders
//                .put("/comment/update/" + commentId))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
//
//    }

    @Test
    public void testDeleteComment() throws Exception {
        BDDMockito
                .given(repo.findById(commentId))
                .willReturn(Optional.of(comments1));

        String expectedContent = "true";//"{\"commentId\":30,\"comments\":\"This is a comment\",\"createdDate\":\"2019-04-15\",\"user\":{\"id\":10,\"name\":\"user1\"}}";
        this.mvc.perform(MockMvcRequestBuilders
                .delete("/comment/delete/" + commentId)
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }


}
