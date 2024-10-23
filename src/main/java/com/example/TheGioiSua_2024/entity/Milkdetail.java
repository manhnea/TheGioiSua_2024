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

    @NotNull(message = "Ngày hết hạn là bắt buộc")
    @FutureOrPresent(message = "Ngày hết hạn phải là ngày hiện tại hoặc trong tương lai")
    private Date expirationdate;

    @NotNull(message = "Giá là bắt buộc")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá không được âm")
    private float price;
    private String imgUrl;
    @NotBlank(message = "Mô tả là bắt buộc")
    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String description;

    @NotNull(message = "Số lượng tồn kho là bắt buộc")
    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    private int stockquantity;

    @Min(value = 0, message = "Trạng thái không hợp lệ")
    @Max(value = 1, message = "Trạng thái không hợp lệ")
    private int status;
}
