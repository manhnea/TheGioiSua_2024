/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.Validator;

import com.example.TheGioiSua_2024.entity.Packagingunit;

/**
 *
 * @author truon
 */
public class PackaginunitValidator {
      public static String validatePackagingUnit(Packagingunit packagingUnit) {
        if (packagingUnit.getPackagingunitname() == null || packagingUnit.getPackagingunitname().isEmpty()) {
           return "Tên đơn vị đóng gói không được để trống";
        } else if (packagingUnit.getPackagingunitname().length() < 3 || packagingUnit.getPackagingunitname().length() > 100) {
           return "Tên đơn vị đóng gói phải có độ dài từ 3 đến 100 ký tự";
        } else if (!packagingUnit.getPackagingunitname().matches("^[\\p{L}0-9\\s,.-/]+$")) {
           return "Tên đơn vị đóng gói chỉ được chứa các ký tự chữ, số và khoảng trắng";
        }
        return null;
    }
}
