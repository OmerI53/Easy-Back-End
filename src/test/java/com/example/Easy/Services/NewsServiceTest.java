package com.example.Easy.Services;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class NewsServiceTest {

    @Autowired
    NewsRepository dNewsRepository;


    public void addNewsTest(){
        NewsEntity newsEntity = NewsEntity.builder()
                .newsId(1245253)
                .title("deneme")
                .image("aqgfaf")
                .text("dgssgs")
                .categories("bilim-kurgu")
                .AuthorId(UUID.fromString("38a2772f-7efa-46c2-a7cd-2968def72ca8"))
                .build();

    }



}
