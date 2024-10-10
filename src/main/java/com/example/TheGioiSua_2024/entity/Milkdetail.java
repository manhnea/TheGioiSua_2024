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

    @NotNull(message = "Ngày hết hạn là bắt buộc")
    @FutureOrPresent(message = "Ngày hết hạn phải là ngày hiện tại hoặc trong tương lai")
    private Date expirationdate;

    @NotNull(message = "Giá là bắt buộc")
    @Min(value = 0, message = "Giá không được âm")
    private float price;

    @NotBlank(message = "Mô tả là bắt buộc")
    private String description;

    @NotNull(message = "Số lượng tồn kho là bắt buộc")
    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    private int stockquantity;

    private int status;
}
