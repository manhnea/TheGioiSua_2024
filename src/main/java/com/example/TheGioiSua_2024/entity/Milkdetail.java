package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "milkdetail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Milkdetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String milkdetailcode;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "milktasteid", nullable = false)
    private Milktaste milkTaste;

    @ManyToOne
    @JoinColumn(name = "packagingunitid")
    private Packagingunit packagingunit;

    @ManyToOne
    @JoinColumn(name = "usagecapacityid", nullable = false)
    private Usagecapacity usageCapacity;

    private String shelflifeofmilk;


    private float price;
    private String imgUrl;

    private String description;


    private int stockquantity;

    private int status;
}
