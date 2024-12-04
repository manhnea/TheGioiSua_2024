package com.example.TheGioiSua_2024.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductlstDto {

  Long productID;
  Long milkBrandID;
  Long milkTypeID;
  Long targetuserID;
  String milktypename;
  String milkbrandname;
  String targetname;
  String productURL;
  String imgUrl;
  int status;
  private float minPrice;
  private float maxPrice;
}
