package com.example.Easy.models;

import com.example.Easy.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID userId;
    private String name;
    private String image;
    private Integer role;
    private String email;
    private String password;

    public UserDTO(UUID userId, String name, String image, Integer role, String email, String password, List<RecordsEntity> userRecords, Set<UserEntity> following, List<CommentEntity> comments, List<DeviceEntity> device, Set<UserEntity> followers, List<NewsEntity> news, String username, Collection<? extends GrantedAuthority> authorities) {

    }
}


