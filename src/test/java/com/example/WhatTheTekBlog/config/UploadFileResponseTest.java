package com.example.WhatTheTekBlog.config;

import com.example.WhatTheTekBlog.WhatTheTekBlogApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WhatTheTekBlogApplication.class)
public class UploadFileResponseTest {

    UploadFileResponse fileResponse = new UploadFileResponse();
    UploadFileResponse fileResponse1 = new UploadFileResponse("Testing FileName","/downloadFile","jpg",5L);

    @Before
    public void setup() {

    }

    @Test
    public void getFileName() {
        String expectedFileName = "Testing FileName";
        fileResponse.setFileName(expectedFileName);
        String actualFileName = fileResponse.getFileName();
        Assert.assertEquals(expectedFileName,actualFileName);
    }

    @Test
    public void getFileDownloadUri() {
        String expectedURI = "/downloadFile";
        fileResponse.setFileDownloadUri(expectedURI);
        String actualFileName = fileResponse.getFileDownloadUri();
        Assert.assertEquals(expectedURI,actualFileName);
    }

    @Test
    public void getFileType() {
        String expectedType = "jpg";
        fileResponse.setFileType(expectedType);
        String actualFileType = fileResponse.getFileType();
        Assert.assertEquals(expectedType,actualFileType);
    }

    @Test
    public void getSize() {
        Long expectedSize = 5L;
        fileResponse.setSize(expectedSize);
        Long actualSize = fileResponse.getSize();
        Assert.assertEquals(expectedSize,actualSize);
    }
}
