package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productcode;
    private String productname;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "milktypeid")
    private MilkType milktype;
    private int status;
}
