package com.example.TheGioiSua_2024.Validator;

import com.example.TheGioiSua_2024.entity.User;

public class UserValidator {
    public static String validateUser(User user) {

        if (user.getFullname() == null || user.getFullname().isEmpty()) {
            return "Họ tên không được để trống";
        } else if (user.getFullname().length() < 3 || user.getFullname().length() > 100) {
            return "Họ tên phải có độ dài từ 3 đến 100 ký tự";
        } else if (!user.getFullname().matches("^[\\p{L}0-9\\s]+$")) {
           return "Họ tên chỉ được chứa chữ cái (tiếng Việt có dấu), chữ số và khoảng trắng.";
        }


        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            return "Địa chỉ không được để trống";
        } else if (user.getAddress().length() < 10 || user.getAddress().length() > 500) {
            return "Địa chỉ phải có độ dài từ 10 đến 500 ký tự";
        } else if (!user.getAddress().matches("^[\\p{L}0-9\\s,.-]+$")) {
            return "Địa chỉ chỉ được chứa chữ cái (tiếng Việt có dấu), chữ số, khoảng trắng, dấu phẩy, dấu chấm và dấu gạch ngang.";
        }


        if (user.getPhonenumber() == null || user.getPhonenumber().isEmpty()) {
            return "Số điện thoại không được để trống";
        } else if (user.getPhonenumber().length() < 10 || user.getPhonenumber().length() > 11) {
            return "Số điện thoại phải có độ dài từ 10 đến 11 ký tự";
        } else if (!user.getPhonenumber().matches("^[0-9]+$")) {
            return "Số điện thoại chỉ được chứa chữ số";
        }
        return null;
    }
}