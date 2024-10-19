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
    Long id;
    String productCode; // Sửa thành camelCase
    String milkdetailcode;
    String packagingunitname; // Sửa thành camelCase
    String milkTypename; // Sửa thành camelCase
    String milkbrandname; // Sửa thành camelCase
    String milktastename; // Sửa thành camelCase
    int capacity; // Sửa thành camelCase
    String unit;
    String targetName; // Sửa thành camelCase
    float price; // Sửa thành camelCase
    int stockquantity; // Sửa thành camelCase
    String description;
    int status;

    
    
}
