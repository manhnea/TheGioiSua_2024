package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Setting;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SettingValidator {

    // Regex pattern for validating URLs (basic version)
    private static final String URL_REGEX = "^(http|https)://[^\\s]+$";

    // Regex pattern for validating email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Regex pattern for validating phone numbers (international format)
    private static final String PHONE_REGEX = "^\\+?[0-9]{1,4}?[0-9]{6,14}$";

    // Regex pattern for validating API keys (basic format, can be adjusted to your needs)
    private static final String APIKEY_REGEX = "^[A-Za-z0-9]{32}$"; // Example: 32 characters alphanumeric API key

    public static Map<String, String> validateSetting(Setting setting) {
        Map<String, String> errors = new HashMap<>();

        // Validate nameshop
        if (setting.getNameshop() == null || setting.getNameshop().isEmpty()) {
            errors.put("nameshop", "Tên cửa hàng không được để trống");
        } else if (setting.getNameshop().length() < 3 || setting.getNameshop().length() > 100) {
            errors.put("nameshop", "Tên cửa hàng phải có độ dài từ 3 đến 100 ký tự");
        }

        // Validate apikey (if provided)
        if (setting.getApikey() != null && !setting.getApikey().matches(APIKEY_REGEX)) {
            errors.put("apikey", "API Key không hợp lệ");
        }

        // Validate valuebank (if provided, should be a valid number)
        if (setting.getValuebank() != null && !isNumeric(setting.getValuebank())) {
            errors.put("valuebank", "Giá trị ngân hàng không hợp lệ");
        }

        // Validate stk (if provided, should be a valid bank account number)
        if (setting.getStk() != null && !isValidBankAccount(setting.getStk())) {
            errors.put("stk", "Số tài khoản không hợp lệ");
        }

        // Validate hotline (if provided, should be a valid phone number)
        if (setting.getHotline() != null && !Pattern.matches(PHONE_REGEX, setting.getHotline())) {
            errors.put("hotline", "Số hotline không hợp lệ");
        }

        // Validate fullname
        if (setting.getFullname() == null || setting.getFullname().isEmpty()) {
            errors.put("fullname", "Họ và tên không được để trống");
        } else if (!setting.getFullname().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            errors.put("fullname", "Họ và tên chỉ được chứa các ký tự chữ và khoảng trắng");
        }

        // Validate email (if provided)
        if (setting.getEmail() != null && !Pattern.matches(EMAIL_REGEX, setting.getEmail())) {
            errors.put("email", "Email không hợp lệ");
        }

        // Validate address (if provided, should be between 5 and 200 characters)
        if (setting.getAddress() != null && (setting.getAddress().length() < 5 || setting.getAddress().length() > 200)) {
            errors.put("address", "Địa chỉ phải có độ dài từ 5 đến 200 ký tự");
        }

        // Validate logo (if provided, should be a valid URL or image path)
        if (setting.getLogo() != null && !Pattern.matches(URL_REGEX, setting.getLogo())) {
            errors.put("logo", "Đường dẫn logo không hợp lệ");
        }

        return errors;
    }

    // Helper method to check if a string is numeric
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Helper method to check if a bank account number is valid
    private static boolean isValidBankAccount(String stk) {
        // Example: Account number should have at least 10 digits
        return stk != null && stk.matches("^[0-9]{10,}$");
    }
}
