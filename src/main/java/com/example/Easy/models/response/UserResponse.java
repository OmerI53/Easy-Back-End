package com.example.Easy.models.response;

import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BaseResponse{
    private UUID userId;
    private String name;
    private String image;
    private String email;
    private Integer role;
    private List<UUID> news;
    private List<UUID> devices;
    private List<UUID> comments;
    private Set<UUID> following;
    private Set<UUID> followers;

    public UserResponse(UserDTO userDTO){
        this.userId = userDTO.getUserId();
        this.name = userDTO.getName();
        this.image = userDTO.getImage();
        this.email = userDTO.getEmail();
        this.role = userDTO.getRole();
        this.news= getDTONews(userDTO.getNews());
    }
    public UserResponse(UserDTO userDTO,boolean follow){
        this.userId = userDTO.getUserId();
        this.name = userDTO.getName();
        this.image = userDTO.getImage();
        this.email = userDTO.getEmail();
        this.role = userDTO.getRole();
    }
    private List<UUID> getDTONews(List<NewsDTO> news){
        if(news==null)
            return null;
        return news
                .stream().map(NewsDTO::getNewsId)
                .collect(Collectors.toList());
    }
}
