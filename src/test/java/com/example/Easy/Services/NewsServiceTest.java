package com.example.Easy.Services;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Mappers.NewsMapper;
import com.example.Easy.Models.NewsCategories;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repository.NewsRepository;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsServiceTest {

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Test
    void bootstrap() {
        List<UserDTO> userDTOList = userService.listUsers();
        NewsDTO new1 = NewsDTO.builder()
                .title("test1")
                .image("https://miro.medium.com/v2/resize:fit:1358/1*cG6U1qstYDijh9bPL42e-Q.jpeg")
                .creationTime(LocalDateTime.now())
                .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eget quam eu massa ultrices scelerisque. Quisque et erat lacinia, cursus tellus et, dignissim est. Ut mollis diam vitae sodales vestibulum. Donec non cursus magna, non sodales ante. Nullam quis pharetra neque. ")
                .authorId(userDTOList.get(0).getUserId())
                .newsCategories(NewsCategories.MACHINE_LEARNING)
                .build();
        NewsDTO new2 = NewsDTO.builder()
                .title("test2")
                .creationTime(LocalDateTime.of(1990,5,22,11,11))
                .image("https://www.mtu.edu/cs/undergraduate/software/what/images/software-engineering-banner2400.jpg")
                .text("Mauris turpis justo, posuere eu imperdiet eu, porta a risus. Nulla maximus mi nec mi lacinia iaculis. Donec viverra in libero a consectetur. Nullam quam turpis, mollis eget elementum vitae, tincidunt vitae tortor. Donec venenatis scelerisque urna non tristique. Nam massa eros, ")
                .authorId(userDTOList.get(0).getUserId())
                .newsCategories(NewsCategories.SOFTWARE_ENGINEERING)
                .build();
        NewsDTO new3 = NewsDTO.builder()
                .title("test3")
                .creationTime(LocalDateTime.of(1000,8,12,20,10))
                .image("https://d1m75rqqgidzqn.cloudfront.net/wp-data/2019/09/11134058/What-is-data-science-2.jpg")
                .text("vulputate ac egestas mollis, fringilla vel est. Donec sit amet nibh nisi. In ac sem ac dui pretium vulputate eu ac ligula. Pellentesque fermentum fringilla mi, id sagittis elit malesuada nec. ")
                .authorId(userDTOList.get(1).getUserId())
                .newsCategories(NewsCategories.DATA_SCIENCE)
                .build();
        NewsDTO new4 = NewsDTO.builder()
                .title("test4")
                .creationTime(LocalDateTime.of(2000,5,10,15,48))
                .image("https://media.geeksforgeeks.org/wp-content/cdn-uploads/20221222184908/web-development1.png")
                .text("Quisque egestas vulputate enim, non pharetra orci pulvinar vitae. Mauris nibh justo, laoreet eget porttitor a, blandit vel justo. Etiam eget lobortis lectus, vitae vulputate lacus. Ut ut congue sapien. Donec a tincidunt velit. Curabitur sed turpis massa. Donec interdum pulvinar ")
                .authorId(userDTOList.get(2).getUserId())
                .newsCategories(NewsCategories.WEB_DEVELOPMENT)
                .build();
        newsService.postNews(new1);
        newsService.postNews(new2);
        newsService.postNews(new3);
        newsService.postNews(new4);
    }


    @Test
    void postTest() {

        NewsDTO new1 = NewsDTO.builder()
                .title("test9")
                .image("https://www.google.com/search?q=machine+learning&sca_esv=559361602&tbm=isch&source=lnms&sa=X&ved=2ahUKEwiC5--r-PKAAxVSYPEDHVsTCq4Q_AUoAnoECAIQBA&biw=1728&bih=931&dpr=2#imgrc=MljwM1234Xl_pM")
                .text("bootstrap9")
                .newsCategories(NewsCategories.DATA_SCIENCE)
                .authorId(UUID.fromString("1"))
                .build();
        newsService.postNews(new1);
    }

    @Test
    void getByTitleTest() {
        List<NewsDTO> list = newsService.getNewsByTitle("test2");
        System.out.println(list);
    }

    @Test
    void getByCategoryTest() {
        List<NewsDTO> list = newsService.getNewsByCategory(NewsCategories.MACHINE_LEARNING);
        System.out.println(list);
    }

    @Test
    void getByNewsId() {
        List<NewsDTO> newsDTOList = newsService.getAllNews();
        NewsDTO newsDTO = newsService.getNewsById(newsDTOList.get(0).getNewsUUID());
        System.out.println(newsDTO);
    }
    @Test
    void getByAuthorId(){
        List<UserDTO> userDTOList = userService.listUsers();
        List<NewsDTO> newsDTO = newsService.getNewsByAuthor(userDTOList.get(0).getUserId());
        System.out.println(newsDTO);
    }

    @Test
    void deleteById() {
        newsService.deletePostById(UUID.fromString("0ed27b57-5fce-445c-b518-2bddc3185449"));
    }



    @Test
    void listNews() {

        List<NewsDTO> newsDTOS = newsService.getAllNews();
        System.out.println(newsDTOS);
    }
}