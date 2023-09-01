package com.example.Easy.Controllers;

import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Models.RecordsDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity createNewUser(@RequestBody UserDTO userDTO){
        //TODO cant bootstrap data since a real FCM is needed
        userService.createNewUser(userDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable("userId") UUID userId){
        userService.deleteUser(userId);
    }
    @PatchMapping("/{userId}")
    public void patchUserById(@PathVariable("userId") UUID userId,@RequestBody UserDTO userDTO){
        userService.patchUserById(userId,userDTO);
    }
    @GetMapping("/all")
    public Page<UserDTO> listUsers(@RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize,
                                   @RequestParam(required = false) String sortBy){
        return userService.listUsers(pageNumber,pageSize, sortBy);
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable("userId") UUID userId){
        return userService.getUserById(userId);
    }

    @PostMapping("/follow/{userId}")
    public void followUserById(@PathVariable("userId") UUID userId,@RequestBody UserDTO userDTO){
        userService.followUserById(userId,userDTO);

    }
    @DeleteMapping("/follow/{userId}")
    public void unfollowUserById(@PathVariable("userId") UUID userId,@RequestBody UserDTO userDTO){
        userService.unfollowUserById(userId,userDTO);
    }

    @GetMapping("/followers/{userId}")
    public Page<UserDTO> getAllFollowersById(@PathVariable("userId") UUID userId,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String sortBy){
        return userService.getAllFollowers(userId, pageNumber,pageSize,sortBy);
    }
    @GetMapping("/following/{userId}")
    public Page<UserDTO> getAllFollowingById(@PathVariable("userId") UUID userId,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String sortBy){
        return userService.getAllFollowing(userId, 1, 25, "name");
    }

    @PostMapping("/records/{userId}")
    public void readNews(@PathVariable("userId") UUID userId ,@RequestBody NewsDTO newsDTO){
        userService.readNews(userId,newsDTO);
    }

    @GetMapping("/records/{userId}")
    public Page<RecordsDTO> getUserRecordsById(@PathVariable("userId") UUID userId,
                                               @RequestParam(required = false) Integer pageNumber,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) String sortBy){
        return userService.getUserRecordsById(userId,pageNumber,pageSize,sortBy);
    }

}
