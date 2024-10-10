package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Mã sản phẩm là bắt buộc")
    @Size(min = 5, max = 20, message = "Mã sản phẩm phải có độ dài từ 5 đến 20 ký tự")
    private String productCode;

    @NotBlank(message = "Tên sản phẩm là bắt buộc")
    @Size(min = 3, max = 50, message = "Tên sản phẩm phải có độ dài từ 3 đến 50 ký tự")
    private String productName;

    @Min(value = 1, message = "Số lượng phải ít nhất là 1")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "milktypeid")
    private MilkType milktype;

    private int status;
}
