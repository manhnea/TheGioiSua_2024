package com.example.TheGioiSua_2024.Validator;

import com.example.TheGioiSua_2024.entity.MilkType;
import java.util.HashMap;
import java.util.Map;

public class MilkTypeValidator {

    public static String validateMilkType(MilkType milkType) {

        if (milkType.getMilkTypename() == null || milkType.getMilkTypename().isEmpty()) {
            return"Tên loại sữa không được để trống";
        } else if (milkType.getMilkTypename().length() < 3 || milkType.getMilkTypename().length() > 100) {
            return "Tên loại sữa phải có độ dài từ 3 đến 100 ký tự";
        } else if (!milkType.getMilkTypename().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            return "Tên loại sữa chỉ được chứa các ký tự chữ, số và khoảng trắng";
        }

        // Validate description
        if (milkType.getDescription() == null || milkType.getDescription().isEmpty()) {
            return "Mô tả không được để trống";
        } else if (milkType.getDescription().length() < 10 || milkType.getDescription().length() > 500) {
            return "Mô tả phải có độ dài từ 10 đến 500 ký tự";
        }

        return null;
    }
}
