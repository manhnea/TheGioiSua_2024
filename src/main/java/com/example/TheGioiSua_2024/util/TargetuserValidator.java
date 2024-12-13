package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Targetuser;
import java.util.HashMap;
import java.util.Map;

public class TargetuserValidator {

    public static Map<String, String> validateTargetuser(Targetuser targetuser) {
        Map<String, String> errors = new HashMap<>();

        if (targetuser.getTargetName() == null || targetuser.getTargetName().isEmpty()) {
            errors.put("targetName", "Tên nhóm khách hàng không được để trống");
        } else if (targetuser.getTargetName().length() < 3 || targetuser.getTargetName().length() > 100) {
            errors.put("targetName", "Tên nhóm khách hàng phải có độ dài từ 3 đến 100 ký tự");
        } else if (!targetuser.getTargetName().matches("^[A-Za-z0-9àáảãạăắằẳẵặâấầẩẫậêếềểễệôốồổỗộơớờởỡợuúùủũụưứừửữự,.-\\s]+$")) {
            // Cập nhật regex để cho phép ký tự tiếng Việt và các ký tự đặc biệt như dấu phẩy, dấu chấm, dấu gạch ngang và khoảng trắng
            errors.put("targetName", "Tên nhóm khách hàng chỉ được chứa chữ cái, chữ số, khoảng trắng và các ký tự tiếng Việt có dấu");
        }

        // Validate description (if provided, should be between 5 and 500 characters)
        if (targetuser.getDescription() != null && (targetuser.getDescription().length() < 5 || targetuser.getDescription().length() > 500)) {
            errors.put("description", "Mô tả phải có độ dài từ 5 đến 500 ký tự");
        }

        return errors;
    }
}
