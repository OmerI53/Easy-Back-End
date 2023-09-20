package com.example.Easy.services;

import com.example.Easy.models.RecordsDTO;
import com.example.Easy.models.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    NewsService newsService;
    @Autowired
    AuthenticationService authenticationService;

    @Test
    void bootstrap(){
        /*
        Random random = new Random();
        List<String> images = new LinkedList<>();
        images.add("https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/User_icon_2.svg/800px-User_icon_2.svg.png");
        images.add("https://cdn.pixabay.com/photo/2020/07/01/12/58/icon-5359553_1280.png");
        images.add("https://t3.ftcdn.net/jpg/05/17/79/88/360_F_517798849_WuXhHTpg2djTbfNf0FQAjzFEoluHpnct.jpg");
        for (int i=0; i<10;i++){
            UserDTO userDTO = UserDTO.builder()
                    .name(String.format("user%d",i))
                    .userToken(String.format("%d",i))
                    .role(1)
                    .image(images.get(random.nextInt(0,images.size())))
                    .email(String.format("user%d@gmail.com",i))
                    .password(String.format("user%d",i))
                    .build();

            authenticationService.register(userDTO);
        }

         */
    }

    @Test
    void addUser(){
        /*
        UserDTO user1 = UserDTO.builder()
                .name("user1")
                .userToken("6")
                .image(null)
                .role(1)
                .build();

         */
    }
    @Test
    void getAllUsers(){
        /*
        Page<UserDTO> userDTOS =userService.listUsers(1, 25, "name");
        System.out.println(userDTOS);

         */
    }

    @Test
    void getUserById(){
/*
        Page<UserDTO> userDTOS =userService.listUsers(1, 25, "name");
        UserDTO user = userService.getUserById(userDTOS.toList().get(0).getUserId());
        System.out.println(user);

 */
    }

    @Test
    void patchUserById(){
        /*
        UserDTO userDTO = UserDTO.builder()
                .name("user6")
                .image("https://cdn-icons-png.flaticon.com/512/1053/1053244.png")
                .build();
        userService.patchUserById(UUID.fromString("3b4e5a25-6e23-43f0-ae63-187fdc1cdfb9"),userDTO);

         */
    }

    @Test
    void readdata(){
        /*
        Random random = new Random();
        Page<UserDTO> allUsers = userService.listUsers(null,null,null);
        Page<NewsDTO> allNews = newsService.getAllNews(null,null,null);
        for(int i =0;i<100;i++){
            UserDTO randUser = allUsers.toList().get(random.nextInt(0,allUsers.getNumberOfElements()));
            NewsDTO randnews = allNews.toList().get(random.nextInt(0,allNews.getNumberOfElements()));
            userService.readNews(randUser.getUserId(),randnews);
        }

         */
    }
    @Test
    void reset(){
        T user = userService.getUser(UUID.fromString("29fa7ff8-9b42-41b5-95fb-c1f3a5f1b117"));
        T userDTO = userService.getUser(UUID.fromString("de26b783-8bfb-4d0e-894f-2020358b0b7a"));
        List<RecordsDTO> recordsDTO = new LinkedList<>();
        user.getUserRecords().clear();
        user.setUserRecords(recordsDTO);
        List<RecordsDTO> recordsDTO2 = new LinkedList<>();
        userDTO.getUserRecords().clear();
        userDTO.setUserRecords(recordsDTO2);
    }


}