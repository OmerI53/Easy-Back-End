package com.example.Easy.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@Builder
public class UserDTO implements UserDetails {
    private UUID userId;
    private String name;
    private String image;
    private List<DeviceDTO> devices;
    private List<NewsDTO> news;
    private List<CommentDTO> comments;
    private Set<UserDTO> following;
    private Set<UserDTO> followers;
    private List<RecordsDTO> userRecords;
    private String email;
    private String password;
    private Integer role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        return userId.equals(userDTO.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
