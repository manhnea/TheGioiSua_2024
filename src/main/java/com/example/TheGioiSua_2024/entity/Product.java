package com.example.TheGioiSua_2024.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private String productname;

    private String productCode;

    @ManyToOne
    @JoinColumn(name = "milktypeid", nullable = false)
    private MilkType milkType;

    @ManyToOne
    @JoinColumn(name = "milkbrandid", nullable = false)
    private Milkbrand milkBrand;

    @ManyToOne
    @JoinColumn(name = "targetuserid", nullable = false)
    private Targetuser targetUser;
    private String productUrl;
    private String imgUrl;
    private int status;
}
