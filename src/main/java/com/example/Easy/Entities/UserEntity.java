package com.example.Easy.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Users")
public class UserEntity implements UserDetails {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID userId;


    @NotNull
    @NotBlank
    private String name;

    private String image;
    //Firebase Messaging token of the user

    //device of the user
    @ManyToMany
    @JoinTable(name = "device_users",
            joinColumns = @JoinColumn(name = "deviceId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<DeviceEntity> device;


    //News written by this user
    @JsonIgnore
    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<NewsEntity> news;
    //Comments written by this user
    @JsonIgnore
    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CommentEntity> comments;

    @ManyToMany
    @JoinTable(name = "follow",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<UserEntity> following;

    @ManyToMany(mappedBy = "following")
    private Set<UserEntity> followers;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RecordsEntity> userRecords;

    @Column(unique = true)
    private String email;

    private String password;

    //Role of the user default it is 1 (cant post)
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
}
