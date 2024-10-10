package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "containerid")
    private Container container;

    @ManyToOne
    @JoinColumn(name = "sizeid")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "milktasteid")
    private Milktaste milktaste;

   @ManyToOne
   @JoinColumn(name = "packagingunitid")
    private Packagingunit packagingunit;

   @ManyToOne
    @JoinColumn(name = "usagecapacityid")
    private Usagecapacity usagecapacity;

   private Date expirationdate;

   private float price;

   private String description;

   private int stockquantity;

   private int status;

}
