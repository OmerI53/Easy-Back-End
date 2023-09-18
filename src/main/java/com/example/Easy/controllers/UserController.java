package com.example.Easy.controllers;

import com.example.Easy.models.*;
import com.example.Easy.services.AuthenticationService;
import com.example.Easy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authenticationService.register(userDTO));
    }
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable("userId") UUID userId){
        userService.delete(userId);
    }
    @PatchMapping("/{userId}")
    public void patchUserById(@PathVariable("userId") UUID userId,@RequestBody UserDTO userDTO){
        userService.patch(userId,userDTO);
    }
    @GetMapping("/all")
    public Page<UserDTO> listUsers(@RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize,
                                   @RequestParam(required = false) String sortBy){
        return userService.get(pageNumber,pageSize, sortBy);
    }
    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable("userId") UUID userId){
        return userService.get(userId);
    }

    @GetMapping("/email/{email}")
    public UserDTO getUserByEmail(@PathVariable("email") String email){
        return userService.find(email);
    }
    @PostMapping("/follow/{userId}") //target of follow is {userId}, person following is userDTO
    public void followUserById(@PathVariable("userId") UUID userId,@RequestBody UserDTO userDTO){
        userService.follow(userId,userDTO);

    }
    @DeleteMapping("/follow/{userId}") //target of follow is {userId}, person following is userDTO
    public void unfollowUserById(@PathVariable("userId") UUID userId,@RequestBody UserDTO userDTO){
        userService.unfollow(userId,userDTO);
    }

    @GetMapping("/followers/{userId}")
    public Page<UserDTO> getAllFollowersById(@PathVariable("userId") UUID userId,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String sortBy){
        return userService.getFollowers(userId, pageNumber,pageSize,sortBy);
    }
    @GetMapping("/following/{userId}")
    public Page<UserDTO> getAllFollowingById(@PathVariable("userId") UUID userId,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String sortBy){
        return userService.getFollowing(userId, pageNumber, pageSize, sortBy);
    }
    @PostMapping("/records/{userId}")
    public void readNews(@PathVariable("userId") UUID userId ,@RequestBody NewsDTO newsDTO){
        userService.readCount(userId,newsDTO);
    }
    @GetMapping("/records/{userId}")
    public Page<RecordsDTO> getUserRecordsById(@PathVariable("userId") UUID userId,
                                               @RequestParam(required = false) Integer pageNumber,
                                               @RequestParam(required = false) Integer pageSize,
                                               @RequestParam(required = false) String sortBy){
        return userService.getRecords(userId,pageNumber,pageSize,sortBy);
    }
    @GetMapping("/likes/{userId}")
    public Page<NewsDTO> getLikedNews(@PathVariable UUID userId,
                                      @RequestParam(required = false) Integer pageNumber,
                                      @RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) String sortBy){
        return userService.getLikedNews(userId,pageNumber,pageSize,sortBy);
    }
    @GetMapping("/bookmark/{userId}")
    public Page<NewsDTO> getBookmarkedNews(@PathVariable UUID userId,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer pageSize,
                                           @RequestParam(required = false) String sortBy){
        return userService.getBookmarkedNews(userId,pageNumber,pageSize,sortBy);
    }
    @GetMapping("/device/{userId}")
    public Page<DeviceDTO> getDevices(@PathVariable UUID userId,
                                      @RequestParam(required = false) Integer pageNumber,
                                      @RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) String sortBy){
        return userService.getDevices(userId,pageNumber,pageSize,sortBy);
    }
}
