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
    Long milktypeID;
    Long milkbrandID;
    String packagingunitname;
    String milkTypename;
    String milkbrandname;
    String milktastename;
    int capacity;
    String unit;
    String targetName;
    float price;
    int stockquantity;
    String description;
    int status;

    
    
}
