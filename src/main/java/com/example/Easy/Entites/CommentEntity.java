package com.example.Easy.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "News")
public class CommentEntity {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "newsId", length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID commentID;

    @NotNull
    @NotBlank
    private String text;

    @ManyToOne
    private UserEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="name")
    @JsonIgnore
    private NewsEntity news;

}
