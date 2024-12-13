package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Packagingunit;

import java.util.HashMap;
import java.util.Map;

public class PackagingUnitValidator {

    public static Map<String, String> validatePackagingUnit(Packagingunit packagingUnit) {
        Map<String, String> errors = new HashMap<>();

        // Validate packagingunitname
        if (packagingUnit.getPackagingunitname() == null || packagingUnit.getPackagingunitname().isEmpty()) {
            errors.put("packagingunitname", "Tên đơn vị đóng gói không được để trống");
        } else if (packagingUnit.getPackagingunitname().length() < 3 || packagingUnit.getPackagingunitname().length() > 100) {
            errors.put("packagingunitname", "Tên đơn vị đóng gói phải có độ dài từ 3 đến 100 ký tự");
        } else if (!packagingUnit.getPackagingunitname().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            errors.put("packagingunitname", "Tên đơn vị đóng gói chỉ được chứa các ký tự chữ, số và khoảng trắng");
        }

        return errors;
    }
}
