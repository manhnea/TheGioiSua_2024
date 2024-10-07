package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "milktaste")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Milktaste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String milktastename;
    private int status;
}
