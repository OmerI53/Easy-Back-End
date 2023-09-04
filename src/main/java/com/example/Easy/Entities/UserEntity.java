package com.example.Easy.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@Builder
@Entity
@Table(name = "Users")
public class UserEntity {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID userId;

    @NotNull
    @NotBlank
    private String name;

    private String image;

    @NotNull
    @NotBlank
    private String userToken;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<NewsEntity> news;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CommentEntity> comments;

    @ManyToMany
    @JoinTable(name = "follow", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<UserEntity> following;

    @ManyToMany(mappedBy = "following")
    private Set<UserEntity> followers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RecordsEntity> userRecors;

    private String email;
    private String password;
    private Integer role;

}
