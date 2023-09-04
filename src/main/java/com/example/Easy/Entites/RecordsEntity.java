package com.example.Easy.Entites;

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

    private int readCount;
}
