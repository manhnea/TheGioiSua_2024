package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import java.util.HashMap;
import java.util.Map;

public class MilkbrandValidator {

    public static Map<String, String> validateMilkbrand(Milkbrand milkbrand) {
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra tên thương hiệu sữa (milkbrandname) cho phép các ký tự tiếng Việt có dấu
        if (milkbrand.getMilkbrandname() == null || milkbrand.getMilkbrandname().isEmpty()) {
            errors.put("milkbrandname", "Tên thương hiệu sữa không được để trống");
        } else if (milkbrand.getMilkbrandname().length() < 3 || milkbrand.getMilkbrandname().length() > 100) {
            errors.put("milkbrandname", "Tên thương hiệu sữa phải có độ dài từ 3 đến 100 ký tự");
        } else if (!milkbrand.getMilkbrandname().matches("^[\\p{L}0-9\\s,.-]+$")) {
            errors.put("milkbrandname", "Tên thương hiệu sữa chỉ được chứa chữ cái (tiếng Việt có dấu), chữ số, khoảng trắng, dấu phẩy, dấu chấm và dấu gạch ngang.");
        }


        // Kiểm tra mô tả (description)
        if (milkbrand.getDescription() == null || milkbrand.getDescription().isEmpty()) {
            errors.put("description", "Mô tả không được để trống");
        } else if (milkbrand.getDescription().length() < 10 || milkbrand.getDescription().length() > 500) {
            errors.put("description", "Mô tả phải có độ dài từ 10 đến 500 ký tự");
        }

        return errors;
    }
}
