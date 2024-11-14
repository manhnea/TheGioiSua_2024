package com.example.TheGioiSua_2024.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySalesGrowthDTO {

  private int year;
  private int month;
  private double totalSalesValue;
  private Double previousMonthSales;
  private Double growthPercentage;
}
