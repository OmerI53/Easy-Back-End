package com.example.Easy.dao;

import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface NewsDao {
    public Page<NewsDTO> get(Integer pageNumber, Integer pageSize, String sortBy);
    public Page<NewsDTO> get(String category, Integer pageNumber, Integer pageSize, String sortBy);
    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String sortBy);
    public Page<NewsDTO> getByTitle(String title, Integer pageNumber, Integer pageSize, String sortBy);
    public NewsDTO get(UUID newsId);
    public void post(NewsDTO newsDTO);
    public void delete(UUID newsUUID);
    public Page<NewsDTO> getRecommendedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy);
    public String uploadImage(MultipartFile file) throws IOException;
    public String likePost(UUID newsId, UserDTO userDTO);
    public String unlikePost(UUID newsId, UserDTO userDTO);
    public String bookmark(UUID newsId, UserDTO userDTO);
    public String removeBookmark(UUID newsId, UserDTO userDTO);
    public int getLikes(UUID newsId, Integer pageNumber, Integer pageSize, String sortBy);
    public int getBookmarks(UUID newsId, Integer pageNumber, Integer pageSize, String sortBy);
    public Page<NewsDTO> getFollowingNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy);

}
