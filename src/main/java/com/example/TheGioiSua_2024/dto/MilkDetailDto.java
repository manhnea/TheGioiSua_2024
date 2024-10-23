/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    Long productID;
    Long milkBrandID;
    Long milktypeID;
    Long targetuserID;
    Long usagecapacityID;
    Long packagingunitID;
    Long milktasteID;
    Long milkDetailID;
    float price;
    int stockquantity;
    String imgURL;
    int status;

    public MilkDetailDto(Long milkDetailID, float price, int stockquantity, String imgURL, int status) {
        this.milkDetailID = milkDetailID;
        this.price = price;
        this.stockquantity = stockquantity;
        this.imgURL = imgURL;
        this.status = status;
    }
    
    
}
