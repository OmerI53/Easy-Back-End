package com.example.Easy.Entities;

import com.example.Easy.Models.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;

@Entity
@Table(name = "roles")
@Getter
@Setter
@RequiredArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType name;
    public RoleEntity(RoleType name){
        this.name=name;
    }

}
