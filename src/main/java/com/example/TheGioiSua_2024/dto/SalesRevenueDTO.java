package com.example.TheGioiSua_2024.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesRevenueDTO {

  private String milkDetailCode;
  private Double totalRevenue;
  private Integer totalQuantity;
  private Integer invoiceCount;
  private String username;
}
