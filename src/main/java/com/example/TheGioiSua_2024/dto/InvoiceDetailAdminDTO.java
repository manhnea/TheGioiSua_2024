package com.example.TheGioiSua_2024.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailAdminDTO {

  private String invoiceCode;
  private String deliveryAddress;
  private String phoneNumber;
  private String milkDetailDescription;
  private Double totalAmount;
  private Integer quantity;
  private String milkTasteName;
  private String milkTypeName;
  private Double capacity;
  private String unit;
}
