package com.example.Easy.Entites;

import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Table(name = "News")
public class NewsEntity {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name ="newsId", length = 36, columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID newsUUID;

    @NotNull
    @NotBlank
    @Column(name = "Title", length = 50)
    private String title;

    @NotNull
    @NotBlank
    @Column(columnDefinition = "LONGTEXT")
    private String text;

    @NotNull
    @NotBlank
    private String image;

    @NotNull
    @Column(updatable = false)
    private LocalDateTime creationTime;

    @NotNull
    @ManyToOne@JoinColumn(name = "Author")
    private UserEntity author;

    @ManyToOne
    private NewsCategoryEntity category;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RecordsEntity> newsRecord;
}