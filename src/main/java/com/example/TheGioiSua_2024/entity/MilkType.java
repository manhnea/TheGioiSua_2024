package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "milktype")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MilkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String milktypename;
    private String description;
    private int status;
}
