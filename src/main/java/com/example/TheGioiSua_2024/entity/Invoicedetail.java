package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoicedetail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Invoicedetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private int quantity;

    @NotNull(message = "Giá không được để trống")
    @Min(value = 0, message = "Giá phải là số không âm")
    private int price;

    @NotNull(message = "Tổng giá không được để trống")
    @Min(value = 0, message = "Tổng giá phải là số không âm")
    private int totalprice;

    private int status;

    @ManyToOne
    @JoinColumn(name = "invoiceid")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "milkdetailid")
    private Milkdetail milkDetail;
}