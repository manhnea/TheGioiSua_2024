/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.Validator;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import java.util.Arrays;

/**
 *
 * @author truon
 */
public class UsagecapacityValidator {

    private static final int MAX_CAPACITY = 10000;  // Example max value for capacity
    private static final String[] a = {"l", "ml", "kg", "g"};

    public static String validateUsagecapacity(Usagecapacity usagecapacity) {
        if (!Arrays.asList(a).contains(usagecapacity.getUnit())) {
            return "Đơn vị đóng gói chỉ có thể là: " + Arrays.toString(a);
        }
        if (usagecapacity.getCapacity() < 1) {
            return "Sức chứa phải là một số nguyên dương";
        }
        // Validate unit
        if (usagecapacity.getUnit() == null || usagecapacity.getUnit().isEmpty()) {
            return "Đơn vị không được để trống";
        } else if (usagecapacity.getUnit().length() < 1 || usagecapacity.getUnit().length() > 50) {
            return "Đơn vị phải có độ dài từ 1 đến 50 ký tự";
        } else if (!usagecapacity.getUnit().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            return "Đơn vị chỉ có thể chứa các ký tự chữ cái, số và khoảng trắng";
        }

        return null;
    }
}
