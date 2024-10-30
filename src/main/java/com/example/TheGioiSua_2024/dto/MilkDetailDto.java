package com.example.TheGioiSua_2024.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Hieu
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MilkDetailDto {
    Long milkDetailID;
    String packagingunitname;
    String milktypename;
    String milkbrandname;
    String milktastename;
    int capacity;
    String unit;
    String targetname;
    float price;
    int stockquantity;
    String imgURL;
    int status;
}
