package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã hóa đơn không được để trống")
    private String invoicecode;

    @CreationTimestamp
    private LocalDateTime creationdate;

    @Min(value = 0, message = "Số tiền giảm giá phải là số không âm")
    private int discountamount;

    @Min(value = 0, message = "Tổng số tiền phải là số không âm")
    private int totalamount;

    private int status;

    @ManyToOne
    @JoinColumn(name = "voucherid")
    private Voucher voucher;
}