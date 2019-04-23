package com.example.WhatTheTekBlog.config;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/createPost/**").hasAuthority("view:users")//.hasAuthority("view:users")
                .antMatchers(HttpMethod.POST, "/users/sign-up").hasAuthority("view:users")
                .antMatchers(HttpMethod.POST, "/users/updatePost/**").hasAuthority("view:users")
                .antMatchers(HttpMethod.GET, "/post/**").permitAll()
                .antMatchers(HttpMethod.GET, "/post/tags/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/post").permitAll()
                .antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users/id/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/posts/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/comments/**").permitAll()
                .antMatchers(HttpMethod.GET, "/tags").permitAll()
                .antMatchers(HttpMethod.GET, "/tags/**").permitAll()
                .antMatchers(HttpMethod.GET, "/tags/posts/**").permitAll()
                .antMatchers(HttpMethod.POST, "/createTag/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/updateTag/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/deleteTags/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/comment/delete/**").permitAll()
                .antMatchers(HttpMethod.GET, "/comments/post/**").permitAll()
                .antMatchers(HttpMethod.GET, "/comments").permitAll()
                .antMatchers(HttpMethod.GET, "/comment/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/comment/update/**").permitAll()
                .antMatchers(HttpMethod.POST, "/comment/create/**").permitAll()
                .antMatchers(HttpMethod.POST, "/uploadFile").permitAll()
                .antMatchers(HttpMethod.POST, "/uploadMultipleFiles").permitAll()
                .antMatchers(HttpMethod.GET, "/downloadFile/**").permitAll()
                .anyRequest().authenticated();
    }

    public static String getAccessToken() {
        String token = "";
        AuthAPI auth = new AuthAPI("whatthetek.auth0.com", "v6OMhNmN0OO3aPQnC9VnEACBDX7COR0N", System.getenv("clientSecret"));
        AuthRequest request = auth.login("testinguser", "Whatthetek!", "Username-Password-Authentication")
                .setAudience("http://localhost:8080")
                .setScope("openid view:user view:users");
        try {
            TokenHolder holder = request.execute();
            token = holder.getAccessToken();
        } catch (Auth0Exception exception) {
            // api error
            exception.printStackTrace();
        }// request error
        return token;
    }

}
