package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "milkbrand")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Milkbrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String milkbrandname;


    private String description;

    private int status;
}
