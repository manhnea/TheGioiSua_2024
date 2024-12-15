package com.example.TheGioiSua_2024.Validator;

import com.example.TheGioiSua_2024.entity.Targetuser;
import java.util.HashMap;
import java.util.Map;

public class TargetuserValidator {

    public static  String validateTargetuser(Targetuser targetuser) {


        if (targetuser.getTargetName() == null || targetuser.getTargetName().isEmpty()) {
            return "Tên nhóm khách hàng không được để trống";
        } else if (targetuser.getTargetName().length() < 3 || targetuser.getTargetName().length() > 100) {
            return "Tên nhóm khách hàng phải có độ dài từ 3 đến 100 ký tự";
        } else if (!targetuser.getTargetName().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            // Cập nhật regex để cho phép ký tự tiếng Việt và các ký tự đặc biệt như dấu phẩy, dấu chấm, dấu gạch ngang và khoảng trắng
            return "Tên nhóm khách hàng chỉ được chứa chữ cái, chữ số, khoảng trắng và các ký tự tiếng Việt có dấu";
        }

        // Validate description (if provided, should be between 5 and 500 characters)
        if (targetuser.getDescription() != null && (targetuser.getDescription().length() < 5 || targetuser.getDescription().length() > 500)) {
            return  "Mô tả phải có độ dài từ 5 đến 500 ký tự";
        }

        return null;
    }
}
