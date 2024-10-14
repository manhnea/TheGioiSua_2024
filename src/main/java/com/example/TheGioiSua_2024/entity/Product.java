package com.example.TheGioiSua_2024.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
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

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm không được dài quá 100 ký tự")
    private String productname;

    @NotBlank(message = "Mã sản phẩm là bắt buộc")
    @JsonProperty("productcode")
    @Size(min = 5, max = 20, message = "Mã sản phẩm phải có độ dài từ 5 đến 20 ký tự")
    private String productCode;

    @ManyToOne
    @JoinColumn(name = "milktypeid")
    private MilkType milkType;

    @ManyToOne
    @JoinColumn(name = "milkbrandid")
    private Milkbrand milkBrand;

    @ManyToOne
    @JoinColumn(name = "targetuserid")
    private Targetuser targetUser;
    private int status;
}
