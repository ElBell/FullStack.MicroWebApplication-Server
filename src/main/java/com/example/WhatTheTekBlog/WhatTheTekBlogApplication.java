package com.example.WhatTheTekBlog;

import com.example.WhatTheTekBlog.Utils.RandomGenerator;
import com.example.WhatTheTekBlog.config.FileStorageProperties;
import com.example.WhatTheTekBlog.models.User;
import com.example.WhatTheTekBlog.models.Comments;
import com.example.WhatTheTekBlog.models.Post;
import com.example.WhatTheTekBlog.models.Tags;
import com.example.WhatTheTekBlog.repositories.CommentsRepository;
import com.example.WhatTheTekBlog.services.CommentsService;
import com.example.WhatTheTekBlog.services.PostService;
import com.example.WhatTheTekBlog.services.TagsService;
import com.example.WhatTheTekBlog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.*;

@SpringBootApplication

@EnableConfigurationProperties ({
		FileStorageProperties.class
})

public class WhatTheTekBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatTheTekBlogApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://whatthetek-client.herokuapp.com");
			}
		};
	}

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@Autowired
	CommentsRepository commentsRepository;

	@Autowired
	TagsService tagsService;


	static List<String> tags = new ArrayList();
	static {
		tags.add("Java");
		tags.add("Castle");
		tags.add("Pineapple");
		tags.add("Mocha");
		tags.add("Oreo");
		tags.add("Python");
		tags.add("Ruby");
		tags.add("Rails");
	}

	public Tags generateTag() {
		Random random = new Random();
		List<Tags> tags = (List<Tags>) tagsService.findAllTags();
		return tags.get(random.nextInt(tags.size()));
	}

	//@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		List<User> users = new ArrayList<>();
		for (String tag : tags) {
			Tags tags1 = new Tags();
			tags1.setTagName(tag);
			tagsService.createTags(tags1);
		}

		for (int i = 0; i < 20; i++) {
			User user = new User();
			String name = RandomGenerator.generateWord() + i;
			user.setName(name);
			users.add(userService.create(user));
		}

		Random random = new Random();

		List<Post> posts = new ArrayList<>();
		List<Comments> comments = new ArrayList<>();
		for (int j = 0; j < 20; j++) {
			Post post = new Post();
			post.setCreator(users.get(random.nextInt(users.size()-1)));
			post.setPostTitle(RandomGenerator.generateWord());
			post.setPostContent(RandomGenerator.generateSentence(random.nextInt(100)));
			post.setPostSummary(RandomGenerator.generateSentence(1));

			Comments comment = new Comments();
			comment.setComments(RandomGenerator.generateSentence(1));
			comment.setUser(users.get(random.nextInt(users.size()-1)));
			comment.setPost(post);
			Comments comments1 = new Comments();
			comments1.setComments(RandomGenerator.generateSentence(1));
			comments1.setUser(users.get(random.nextInt(users.size()-1)));
			comments1.setPost(post);
			comments.add(comment);
			comments.add(comments1);
			posts.add(post);
			postService.createPost(post);
			for (int i = 0; i < random.nextInt(tags.size()-2); i++) {
				Tags tags = generateTag();
				tags.addPost(post);
				tagsService.update(tags.getId(), tags);
			}
		}
		comments.forEach(comments1 -> commentsRepository.save(comments1));
	}
}
