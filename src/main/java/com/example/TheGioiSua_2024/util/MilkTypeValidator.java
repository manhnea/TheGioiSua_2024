package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.MilkType;
import java.util.HashMap;
import java.util.Map;

public class MilkTypeValidator {

    public static Map<String, String> validateMilkType(MilkType milkType) {
        Map<String, String> errors = new HashMap<>();

        // Validate milkTypename
        if (milkType.getMilkTypename() == null || milkType.getMilkTypename().isEmpty()) {
            errors.put("milkTypename", "Tên loại sữa không được để trống");
        } else if (milkType.getMilkTypename().length() < 3 || milkType.getMilkTypename().length() > 100) {
            errors.put("milkTypename", "Tên loại sữa phải có độ dài từ 3 đến 100 ký tự");
        } else if (!milkType.getMilkTypename().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            errors.put("milkTypename", "Tên loại sữa chỉ được chứa các ký tự chữ, số và khoảng trắng");
        }

        // Validate description
        if (milkType.getDescription() == null || milkType.getDescription().isEmpty()) {
            errors.put("description", "Mô tả không được để trống");
        } else if (milkType.getDescription().length() < 10 || milkType.getDescription().length() > 500) {
            errors.put("description", "Mô tả phải có độ dài từ 10 đến 500 ký tự");
        }

        return errors;
    }
}
