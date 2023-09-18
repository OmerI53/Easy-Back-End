package com.example.Easy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "News Categories")
public class NewsCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true)
    private String name;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private NewsCategoryEntity parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<NewsCategoryEntity> children;

    @JsonIgnore
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<NewsEntity> news;

    @OneToMany(mappedBy = "newsCategory",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RecordsEntity> categoryRecord;

}
