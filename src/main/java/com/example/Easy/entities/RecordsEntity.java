package com.example.Easy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Records")
public class RecordsEntity {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID recordId;

    @ManyToOne
    private UserEntity user;

    @JsonIgnore
    @ManyToOne
    private CategoryEntity newsCategory;

    @ManyToOne
    private NewsEntity news;

    //number of time a user read the news
    @Column(columnDefinition = "int default 1")
    private int repeatedRead;

    //if post is liked true
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean postlike;
    //if post is bookmarked true
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean postbookmark;

}
