/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.util;

import java.text.Normalizer;

/**
 *
 * @author Hieu
 */
public class StringUtil {
    public static String removeAccent(String s) {
        // Chuẩn hóa chuỗi về dạng NFD (Normalization Form Decomposition)
        String normalizedString = Normalizer.normalize(s, Normalizer.Form.NFD);
        // Loại bỏ các dấu tổ hợp (diacritical marks)
        String result = normalizedString.replaceAll("\\p{M}", "");
        return result;
    }
    public static String replaceSpacesWithUnderscore(String input) {
        // Sử dụng replaceAll để thay thế tất cả khoảng trắng bằng dấu gạch dưới
        return input.replaceAll("\\s+", "_");
    }
}
