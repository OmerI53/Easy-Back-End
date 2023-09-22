package com.example.Easy.services;

import com.example.Easy.mappers.CategoryMapper;
import com.example.Easy.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class NewsServiceTest {

    @Autowired
    NewsService newsService;
    @Autowired
    CategoryService newsCategoryService;
    @Autowired
    CategoryMapper newsCategoryMapper;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @Test
    void bootstrap() {
    /*
        List<NewsCategoryDTO> newsCategoryDTOS = newsCategoryService.getAllCategories();
        Page<UserDTO> userDTOList = userService.listUsers(1, 25, "name");
        CreateNewsRequest new1 = CreateNewsRequest.builder()
                .title("test1")
                .image("https://miro.medium.com/v2/resize:fit:1358/1*cG6U1qstYDijh9bPL42e-Q.jpeg")
                .creationTime(LocalDateTime.now())
                .category(newsCategoryDTOS.get(0))
                .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eget quam eu massa ultrices scelerisque. Quisque et erat lacinia, cursus tellus et, dignissim est.")
                .author(userDTOList.toList().get(0))
                .build();
        CreateNewsRequest new2 = CreateNewsRequest.builder()
                .title("test2")
                .category(newsCategoryDTOS.get(1))
                .creationTime(LocalDateTime.of(2018,5,22,11,11))
                .image("https://www.mtu.edu/cs/undergraduate/software/what/images/software-engineering-banner2400.jpg")
                .text("Mauris turpis justo, posuere eu imperdiet eu, porta a risus. Nulla maximus mi nec mi lacinia iaculis. Donec viverra in libero a consectetur.")
                .author(userDTOList.toList().get(0))
                .build();
        CreateNewsRequest new3 = CreateNewsRequest.builder()
                .title("test3")
                .category(newsCategoryDTOS.get(1))
                .creationTime(LocalDateTime.of(2010,8,12,20,10))
                .image("https://d1m75rqqgidzqn.cloudfront.net/wp-data/2019/09/11134058/What-is-data-science-2.jpg")
                .text("vulputate ac egestas mollis, fringilla vel est. Donec sit amet nibh nisi. In ac sem ac dui pretium vulputate eu ac ligula. ")
                .author(userDTOList.toList().get(1))
                .build();
        CreateNewsRequest new4 = CreateNewsRequest.builder()
                .title("test4")
                .creationTime(LocalDateTime.of(2012,5,10,15,48))
                .image("https://media.geeksforgeeks.org/wp-content/cdn-uploads/20221222184908/web-development1.png")
                .text("Quisque egestas vulputate enim, non pharetra orci pulvinar vitae. Mauris nibh justo, laoreet eget porttitor a, blandit vel justo.")
                .author(userDTOList.toList().get(1))
                .build();
        newsService.postNews(new1);
        newsService.postNews(new2);
        newsService.postNews(new3);
        newsService.postNews(new4);

     */
    }


    @Test
    void postTest() {
        /*
        UserDTO userDTO =userService.listUsers(1, 25, "name").toList().get(1);
        NewsDTO new1 = NewsDTO.builder()
                .title("test9")
                .image("https://www.google.com/search?q=machine+learning&sca_esv=559361602&tbm=isch&source=lnms&sa=X&ved=2ahUKEwiC5--r-PKAAxVSYPEDHVsTCq4Q_AUoAnoECAIQBA&biw=1728&bih=931&dpr=2#imgrc=MljwM1234Xl_pM")
                .text("bootstrap9")
                .author(userDTO)
                .build();
        newsService.postNews(new1);

         */
    }

    @Test
    void getByTitleTest() {
    }


    @Test
    void getByCategoryTest() {
    }




    @Test
    void getByNewsId() {
    }

    @Test
    void getByAuthorId(){
    }
    @Test
    void deleteById() {
        newsService.deletePostById(UUID.fromString("0ed27b57-5fce-445c-b518-2bddc3185449"));
    }



    @Test
    void listNews() {
        /*
        Page<NewsDTO> newsDTOS = newsService.getAllNews(1, 25, "creationTime");
        System.out.println(newsDTOS);

         */
    }
}