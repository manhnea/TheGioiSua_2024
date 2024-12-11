package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import java.util.HashMap;
import java.util.Map;

public class MilkbrandValidator {

    public static Map<String, String> validateMilkbrand(Milkbrand milkbrand) {
        Map<String, String> errors = new HashMap<>();

        // Validate milkbrandname
        if (milkbrand.getMilkbrandname() == null || milkbrand.getMilkbrandname().isEmpty()) {
            errors.put("milkbrandname", "Tên thương hiệu sữa không được để trống");
        } else if (milkbrand.getMilkbrandname().length() < 3 || milkbrand.getMilkbrandname().length() > 100) {
            errors.put("milkbrandname", "Tên thương hiệu sữa phải có độ dài từ 3 đến 100 ký tự");
        } else if (!milkbrand.getMilkbrandname().matches("^[A-Za-z0-9 ]+$")) {
            errors.put("milkbrandname", "Tên thương hiệu sữa chỉ được chứa các ký tự chữ, số và khoảng trắng");
        }

        // Validate description
        if (milkbrand.getDescription() == null || milkbrand.getDescription().isEmpty()) {
            errors.put("description", "Mô tả không được để trống");
        } else if (milkbrand.getDescription().length() < 10 || milkbrand.getDescription().length() > 500) {
            errors.put("description", "Mô tả phải có độ dài từ 10 đến 500 ký tự");
        }

        return errors;
    }
}
