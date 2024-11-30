package com.example.TheGioiSua_2024.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class TotalStatistics {

  private String milkdetailcode;
  private Double totalRevenue;
  private Long totalQuantitySold;
  private Long totalInvoice;
  private String username;
}
