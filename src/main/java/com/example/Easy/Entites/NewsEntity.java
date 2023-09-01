package com.example.Easy.Entites;

import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@Table(name = "News")
public class NewsEntity {
    @Id
    @NotNull
    @NotBlank
    private UUID newsUUID;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String text;
    @NotNull
    @NotBlank
    private String image;
}
