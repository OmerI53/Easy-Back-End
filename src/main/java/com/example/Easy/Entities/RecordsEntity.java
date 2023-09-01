package com.example.Easy.Entities;

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
    @Column(name = "recordId",length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID recordId;

    @ManyToOne
    private UserEntity user;

    @JsonIgnore
    @ManyToOne
    private NewsCategoryEntity newsCategory;

    @ManyToOne
    private NewsEntity news;

    private int repeatedRead;
}
