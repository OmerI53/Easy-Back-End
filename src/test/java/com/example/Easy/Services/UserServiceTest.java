package com.example.Easy.Services;

import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Models.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void bootstrap(){
        UserDTO user1 = UserDTO.builder()
                .name("user1")
                .userToken("1")
                .image(null)
                .build();
        UserDTO user2 = UserDTO.builder()
                .name("user2")
                .userToken("2")
                .image("https://upload.wikimedia.org/wikipedia/commons/thumb/1/12/User_icon_2.svg/800px-User_icon_2.svg.png")
                .build();
        UserDTO user3 = UserDTO.builder()
                .name("user3")
                .userToken("3")
                .image("https://t3.ftcdn.net/jpg/05/17/79/88/360_F_517798849_WuXhHTpg2djTbfNf0FQAjzFEoluHpnct.jpg")
                .build();
        UserDTO user4 = UserDTO.builder()
                .name("user4")
                .userToken("4")
                .image("https://cdn.pixabay.com/photo/2020/07/01/12/58/icon-5359553_1280.png")
                .build();

        userService.createNewUser(user1);
        userService.createNewUser(user2);
        userService.createNewUser(user3);
        userService.createNewUser(user4);
    }
    @Test
    void getAllUsers(){
        List<UserDTO> userDTOS =userService.listUsers();
        System.out.println(userDTOS);
    }

    @Test
    void getUserById(){

        List<UserDTO> userDTOS =userService.listUsers();
        UserDTO user = userService.getUserById(userDTOS.get(0).getUserId());
        System.out.println(user);
    }


}