package com.example.Easy.controllers;

import com.example.Easy.models.response.*;
import com.example.Easy.requests.CreateReadRequest;
import com.example.Easy.requests.CreateUserRequest;
import com.example.Easy.requests.FollowRequest;
import com.example.Easy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResourceBundleMessageSource source;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> listUsers(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) Integer pageNumber,
                                                        @RequestParam(required = false) Integer pageSize,
                                                        @RequestParam(required = false) String sortBy) {
        return new ResponseEntity<>(userService.listUsers(name, pageNumber, pageSize, sortBy).map(UserResponse::new), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<>(new UserResponse(userService.getUser(userId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LoginResponse> register(@RequestBody CreateUserRequest createUserRequest) {
        try {
            return new ResponseEntity<>(new LoginResponse(createUserRequest, userService.register(createUserRequest)), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new NullPointerException(source.getMessage("user.duplicate.email", null, LocaleContextHolder.getLocale()));
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> patchUserById(@PathVariable("userId") UUID userId, @ModelAttribute CreateUserRequest createUserRequest)
            throws IOException {
        return new ResponseEntity<>(new UserResponse(userService.patchUserById(userId, createUserRequest)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<>(new UserResponse(userService.deleteUser(userId)), HttpStatus.CREATED);
    }

    //----Followers----
    @GetMapping("/{userId}/followers")
    public Page<UserResponse> getAllFollower(@PathVariable("userId") UUID userId,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String sortBy) {
        return userService.getAllFollowers(userId, pageNumber, pageSize, sortBy).map(x -> new UserResponse(x, true));
    }

    @PostMapping("/follow") //target of follow is {userId}, person following is userDTO
    public ResponseEntity<FollowResponse> followUserById(@RequestBody FollowRequest followRequest) {
        return new ResponseEntity<>(new FollowResponse(userService.followUser(followRequest)), HttpStatus.CREATED);
    }

    @DeleteMapping("/follow") //target of follow is {userId}, person following is userDTO
    public ResponseEntity<FollowResponse> unfollowUserById(@RequestBody FollowRequest followRequest) {
        return new ResponseEntity<>(new FollowResponse(userService.unfollowUser(followRequest)), HttpStatus.CREATED);
    }

    //----Following----
    @GetMapping("/{userId}/following")
    public ResponseEntity<Page<UserResponse>> getAllFollowing(@PathVariable("userId") UUID userId,
                                                              @RequestParam(required = false) Integer pageNumber,
                                                              @RequestParam(required = false) Integer pageSize,
                                                              @RequestParam(required = false) String sortBy) {
        return new ResponseEntity<>(userService.getAllFollowing(userId, pageNumber, pageSize, sortBy).map(x -> new UserResponse(x, true)), HttpStatus.OK);
    }

    //----Records----
    @PostMapping("/records")
    public ResponseEntity<RecordsResponse> readNews(@RequestBody CreateReadRequest createReadRequest) {
        return new ResponseEntity<>(new RecordsResponse(userService.readNews(createReadRequest)), HttpStatus.CREATED);
    }

    //----User-Fields----
    @GetMapping("/{userId}/records")
    public ResponseEntity<Page<RecordsResponse>> getUserRecordsById(@PathVariable("userId") UUID userId,
                                                                    @RequestParam(required = false) Integer pageNumber,
                                                                    @RequestParam(required = false) Integer pageSize,
                                                                    @RequestParam(required = false) String sortBy,
                                                                    @RequestParam(required = false) Boolean like,
                                                                    @RequestParam(required = false) Boolean bookmark,
                                                                    @RequestHeader(name = "Accept-Language", required = false) final Locale locale) {
        return new ResponseEntity<>(userService.getUserRecordsById(userId, pageNumber, pageSize, sortBy, like, bookmark).map(RecordsResponse::new), HttpStatus.OK);
    }

    @GetMapping("/{userId}/devices")
    public ResponseEntity<Page<DeviceResponse>> getDevices(@PathVariable UUID userId,
                                                           @RequestParam(required = false) Integer pageNumber,
                                                           @RequestParam(required = false) Integer pageSize,
                                                           @RequestParam(required = false) String sortBy,
                                                           @RequestHeader(name = "Accept-Language", required = false) final Locale locale) {
        return new ResponseEntity<>(userService.getDevices(userId, pageNumber, pageSize, sortBy).map(DeviceResponse::new), HttpStatus.OK);
    }
    /*
    @GetMapping("/following/{userId}")//In user or news ??
    public Page<NewsDTO> getFollowingNews(@PathVariable("userId") UUID userId,
                                          @RequestParam(required = false) Integer pageNumber,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String sortBy){
        return newsService.getFollowingNews(userId,pageNumber,pageSize,sortBy);
    }


    @GetMapping("/{email}") // why needed??
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable("email") String email,
                                  @RequestHeader(name = "Accept-Language", required = false) final Locale locale){
        return new ResponseEntity<>(new UserResponse(userService.getUserByEmail(email)),HttpStatus.OK);
    }
     */
}
