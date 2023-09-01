package com.example.Easy.Controllers;

import com.example.Easy.Models.UserDTO;
import com.example.Easy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity AddNewUser(@RequestBody UserDTO userDTO){
        userService.AddNewUser(userDTO);
        return new ResponseEntity((HttpStatus.CREATED));
    }
    @DeleteMapping("/{userID}")
    public void deleteUserByID(@PathVariable("userID")UUID userID){
        userService.getUserByID(userID);
    }
    @PatchMapping("/{userID}")
    public void deleteAllUsers(@PathVariable("userID")UUID userID){
        userService.DeleteAllUsers();
    }
    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{userID}")
    public UserDTO getUserByID(@PathVariable("userID") UUID uuid){
        return userService.getUserByID(uuid);
    }

}
