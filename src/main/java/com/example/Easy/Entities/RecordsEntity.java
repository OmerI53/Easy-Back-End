package com.example.Easy.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "Records"
)
public class RecordsEntity {
    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "recordId", length = 36, columnDefinition = "varchar(36)", nullable = false,updatable = false)
    private UUID recordID;

    @ManyToOne
    private UserEntity user;

    @JsonIgnore
    @ManyToOne
    private NewsCategoryEntity newsCategory;

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
