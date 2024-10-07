package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usagecapacity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usagecapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int capacity;
    private String unit;
    private int status;
}
