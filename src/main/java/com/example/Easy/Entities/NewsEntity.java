package com.example.Easy.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;

import java.util.UUID;

@Entity
@Table(name ="News")
@Getter
@Setter
@RequiredArgsConstructor
@Data
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "newsId",nullable = false,updatable = true,length = 30)
    private Long newsId;

    @Column(name = "categories",nullable = true,updatable = true)
    private String categories;

    @Column(name = "title",nullable = false,updatable = true,length = 100)
    private String title;

    @Column(name = "text",nullable = false,updatable = true,length = 3000)
    private String text;

    @Column(name = "image",nullable = false,updatable = true)
    private String image;

    @Column(name = "AuthorId",nullable = false)
    private UUID AuthorId;
}
