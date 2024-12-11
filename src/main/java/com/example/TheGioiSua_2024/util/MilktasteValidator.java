package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Milktaste;
import java.util.HashMap;
import java.util.Map;

public class MilktasteValidator {

    public static Map<String, String> validateMilktaste(Milktaste milktaste) {
        Map<String, String> errors = new HashMap<>();

        // Validate milktastename
        if (milktaste.getMilktastename() == null || milktaste.getMilktastename().isEmpty()) {
            errors.put("milktastename", "Tên hương vị sữa không được để trống");
        } else if (milktaste.getMilktastename().length() < 3 || milktaste.getMilktastename().length() > 100) {
            errors.put("milktastename", "Tên hương vị sữa phải có độ dài từ 3 đến 100 ký tự");
        } else if (!milktaste.getMilktastename().matches("^[A-Za-z0-9 ]+$")) {
            errors.put("milktastename", "Tên hương vị sữa chỉ được chứa các ký tự chữ, số và khoảng trắng");
        }

        return errors;
    }
}
