
package com.example.Easy.Services;
        import com.example.Easy.Models.UserDTO;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;

        import java.util.List;
        import java.util.UUID;

        import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;


    @Test
    void addUser(){
        UserDTO user1 = UserDTO.builder()
                .name("user1")
                .image("null")
                .build();
        userService.AddNewUser(user1);
    }

}