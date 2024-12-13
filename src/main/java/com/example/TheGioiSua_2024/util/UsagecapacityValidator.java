package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import java.util.HashMap;
import java.util.Map;

public class UsagecapacityValidator {

    private static final int MAX_CAPACITY = 10000;  // Example max value for capacity

    public static Map<String, String> validateUsagecapacity(Usagecapacity usagecapacity) {
        Map<String, String> errors = new HashMap<>();

        // Validate capacity (positive integer and not greater than MAX_CAPACITY)
        if (usagecapacity.getCapacity() <= 0) {
            errors.put("capacity", "Sức chứa phải là một số nguyên dương");
        } else if (usagecapacity.getCapacity() > MAX_CAPACITY) {
            errors.put("capacity", "Sức chứa không được vượt quá " + MAX_CAPACITY);
        }

        // Validate unit
        if (usagecapacity.getUnit() == null || usagecapacity.getUnit().isEmpty()) {
            errors.put("unit", "Đơn vị không được để trống");
        } else if (usagecapacity.getUnit().length() < 1 || usagecapacity.getUnit().length() > 50) {
            errors.put("unit", "Đơn vị phải có độ dài từ 1 đến 50 ký tự");
        } else if (!usagecapacity.getUnit().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            errors.put("unit", "Đơn vị chỉ có thể chứa các ký tự chữ cái, số và khoảng trắng");
        }

        return errors;
    }
}
