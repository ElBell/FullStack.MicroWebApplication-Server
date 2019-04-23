package com.example.WhatTheTekBlog.controllers;

import com.example.WhatTheTekBlog.config.SecurityConfig;
import com.example.WhatTheTekBlog.models.Comments;
import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.models.User;
import com.example.WhatTheTekBlog.services.PostService;
import com.example.WhatTheTekBlog.services.UserService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PostService postService;
    

    @Test
    public void testFindById() throws Exception {
        Integer givenId = 1;
        User user = new User();
        user.setId(1);
        user.setName("New User!");
        BDDMockito
                .given(userService.findById(givenId))
                .willReturn(user);

        String expectedContent = "{\"id\":1,\"name\":\"New User!\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/users/id/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testFindAll() throws Exception {
        int givenId = 2;
        User user = new User();
        user.setId(givenId);
        user.setName("testUser");
        User user1 = new User();
        user1.setId(3);
        user1.setName("testUser2");
        userService.create(user);
        userService.create(user1);
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        Iterable<User> testUsers = users;
        BDDMockito
                .given(userService.findAllUsers())
                .willReturn(testUsers);

        String expectedContent = "[{\"id\":2,\"name\":\"testUser\"},{\"id\":3,\"name\":\"testUser2\"}]";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/users/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testUpdate() throws Exception {
        int givenId = 2;
        User user = new User();
        user.setId(givenId);
        user.setName("testUser");
        BDDMockito.
                given(userService.create(any(User.class)))
                .willReturn(user);

        String input = "{\"id\":123412,\"name\":\"other\",\"posts\":[],\"comments\":[]}";
        this.mvc.perform(MockMvcRequestBuilders
                .put("/users/update/" + givenId)
                .content(input)
                .header("Authorization", "Bearer " + SecurityConfig.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testSignup() throws Exception{
        System.out.println(System.getenv("clientSecret"));
        int givenId = 2;
        User user = new User();
        user.setId(givenId);
        user.setName("testUser");
        BDDMockito.
                given(userService.create(any(User.class)))
                .willReturn(user);

        String expectedContent = "{\"id\":2,\"name\":\"testUser\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .post("/users/sign-up")
                .content("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlFUUXdNRFk1TXpFek5qazFOREUwTnpFNE5FSXpNREl6UlVZMU5UUTBOamRDTWpZNE5qQTBOdyJ9.eyJuaWNrbmFtZSI6InRlc3RpbmcxMjM1IiwibmFtZSI6ImRpdGljQG1haWxsaW5rLmxpdmUiLCJwaWN0dXJlIjoiaHR0cHM6Ly9zLmdyYXZhdGFyLmNvbS9hdmF0YXIvMGVmMWNkMjYxNjc1MzBmMDIwM2ExMTBiYzU3Yjg4YTc_cz00ODAmcj1wZyZkPWh0dHBzJTNBJTJGJTJGY2RuLmF1dGgwLmNvbSUyRmF2YXRhcnMlMkZkaS5wbmciLCJ1cGRhdGVkX2F0IjoiMjAxOS0wNC0xN1QxNjoxMDo0OC41MjJaIiwiaXNzIjoiaHR0cHM6Ly93aGF0dGhldGVrLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw1Y2I3NTAwODcyNDI5YjExNDgwMGY2YTIiLCJhdWQiOiJ2Nk9NaE5tTjBPTzNhUFFuQzlWbkVBQ0JEWDdDT1IwTiIsImlhdCI6MTU1NTUyMjI3NiwiZXhwIjoxNTU1NTU4Mjc2LCJhdF9oYXNoIjoiYVFyTzB3WFJtS3JSVmZnSjE1VTZfQSIsIm5vbmNlIjoiLUlpRklnLWlydWRqNUtzRjltMGhrblZ2cFhQRGNSY1IifQ.Eq63Ta9VNUS9ITonZu-U9SaAkINeWawyGgg2rxlaPUPEtPHAjC2egaGzdZJAUXVmsySK2Is_0u9KvA-XQDuSv5rVxhxXQzn2jqePqzb_rIBUXALEtPTKiGGWsjX2_qiay_1x63K1nAWWSM0eVR0Fg67L01VCmRUAIoVZvPCmQHAccA71lvsDttvU0vFgBJ8NCJxBoG4ZQsRRdgHuBx_BmFH20B_hPp5WdWmQkl74UOL2aJaDn2VsHem4ZzGFJnjC3WlqxOamPkX9IVyKpcRvFb9VFEQxM5j2_nBrkyG5OLX20zEJlahEfU5RXeGUsq-nJFziJvQ52ZR2PI6MHWJ55w")
                .header("authorization", "Bearer " + SecurityConfig.getAccessToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testFindByName() throws Exception{
        String expected = "New User!";
        User user = new User();
        user.setId(1);
        user.setName(expected);
        BDDMockito
                .given(userService.findByName(expected))
                .willReturn(user);

        String expectedContent = "{\"id\":1,\"name\":\"New User!\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/users/name/" + expected))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }


    @Test
    public void testFindPostsByUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("New User!");
        Set<Post> posts = new HashSet<>();
        Post post1 = new Post();
        post1.setPostTitle("TestingTitle");
        post1.setCreator(user);
        posts.add(post1);
        Post post2 = new Post();
        post2.setPostTitle("TestingTitle22qhrlihafs");
        post2.setCreator(user);
        posts.add(post2);
        user.setPosts(posts);
        System.out.println(user.getPosts());
        userService.create(user);
        BDDMockito
                .given(postService.findAll())
                .willReturn(posts);
        BDDMockito
                .given(userService.findById(1))
                .willReturn(user);

        String expectedContent = "[]";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/users/posts/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }


    @Test
    public void testFindCommentsByUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("New User!");
        Set<Comments> posts = new HashSet<>();
        Comments post1 = new Comments();
        post1.setComments("TestingTitle");
        post1.setUser(user);
        posts.add(post1);
        Comments post2 = new Comments();
        post2.setComments("TestingTitle22qhrlihafs");
        post2.setUser(user);
        posts.add(post2);
        user.setComments(posts);
        System.out.println(user.getPosts());
        userService.create(user);
        BDDMockito
                .given(userService.findById(1))
                .willReturn(user);

        String expectedContent = "[]";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/users/comments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

}