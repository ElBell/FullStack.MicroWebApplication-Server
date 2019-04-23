package com.example.WhatTheTekBlog.services;

import com.example.WhatTheTekBlog.WhatTheTekBlogApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WhatTheTekBlogApplication.class)
public class FileStorageExceptionTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test(expected = RuntimeException.class)
    public void testFileStorageException() throws Exception {
//        expectedEx.expect(RuntimeException.class);
//        expectedEx.expectMessage("File is Null");
    }

}
