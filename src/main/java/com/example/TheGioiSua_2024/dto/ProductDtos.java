package com.example.TheGioiSua_2024.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDtos {

  private Long productID;
  private Long milkBrandID;
  private Long milkTypeID;
  private Long targetuserID;
  private String productCode;
  private String productName;
  private String productURL;
  private String imgUrl;
  private int status;
}
