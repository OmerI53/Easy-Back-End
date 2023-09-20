package com.example.Easy.dao;

import com.example.Easy.models.DeviceDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.RecordsDTO;
import com.example.Easy.models.UserDTO;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    public void delete(UUID userId);
    public UserDTO get(UUID userId);
    public Page<UserDTO> get(Integer pageNumber, Integer pageSize, String sortBy);
    public void patch(UUID userId, UserDTO userDTO);
    public void follow(UUID userId, UserDTO userDTO);
    public void unfollow(UUID userId, UserDTO userDTO);
    public Page<UserDTO> getFollowers(UUID userId, Integer pageNumber, Integer pageSize, String sortBy);
    public Page<UserDTO> getFollowing(UUID userId, Integer pageNumber, Integer pageSize, String sortBy);
    public void readCount(UUID userId, NewsDTO newsDTO);
    public Page<RecordsDTO> getRecords(UUID userId, Integer pageNumber, Integer pageSize, String sortBy);
    public UserDTO find(String email);
    public Page<NewsDTO> getLikedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy);
    public Page<NewsDTO> getBookmarkedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy);
    public Page<DeviceDTO> getDevices(UUID userId, Integer pageNumber, Integer pageSize, String sortBy);
}
