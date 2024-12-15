package com.example.TheGioiSua_2024.Validator;

import com.example.TheGioiSua_2024.entity.Milktaste;

import java.util.HashMap;
import java.util.Map;

public class MilkTasteValidator {
    public static  String validateMilktaste(Milktaste milktaste) {
        if (milktaste.getMilktastename() == null || milktaste.getMilktastename().isEmpty()) {
            return  "Tên hương vị sữa không được để trống";
        } else if (milktaste.getMilktastename().length() < 3 || milktaste.getMilktastename().length() > 100) {
            return  "Tên hương vị sữa phải có độ dài từ 3 đến 100 ký tự";
        } else if (!milktaste.getMilktastename().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            return  "Tên hương vị sữa chỉ được chứa các ký tự chữ, số và khoảng trắng";
        }

        return null;
    }
}
