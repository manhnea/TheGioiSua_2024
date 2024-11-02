package com.example.TheGioiSua_2024.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentLinkRequestBody {
    private String productName;
    private String description;
    private int price;
}