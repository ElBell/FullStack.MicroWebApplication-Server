package com.example.WhatTheTekBlog;

import com.example.WhatTheTekBlog.models.Comments;
import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.models.Tags;
import com.example.WhatTheTekBlog.models.User;
import com.example.WhatTheTekBlog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class WhatTheTekBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatTheTekBlogApplication.class, args);
	}

	@Autowired
	UserService userService;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		for (int i = 1; i <= 10; i++) {
			User user = new User();
			user.setId(i);
			user.setName("user" + i);
			for (int j = 0; j < 5; j++) {
				Post post = new Post();
				post.setCreator(user);
				post.setPostTitle(String.format("Title %d%d", i, j));
				post.setPostContent(String.format("Coooooonnnnnnnttttteeeeennnnnttttt"));
				post.setPostSummary("trialPost");
				Tags tags = new Tags();
				tags.setTagName("ThisIsAboutStuff" + i+j);
				Set<Post> postList = new HashSet<>();
				Set<Tags> tags1 = new HashSet<>();
				tags1.add(tags);
				postList.add(post);
				tags.setListOfPosts(postList);
				post.setTagsSet(tags1);


				Comments comments = new Comments();
				comments.setComments(String.format("Comment %d from user %d", j, i));
				comments.setUser(user);
				comments.setPost(post);
				user.addPost(post);
				user.addComment(comments);
			}
			userService.create(user);
		}
	}


}
