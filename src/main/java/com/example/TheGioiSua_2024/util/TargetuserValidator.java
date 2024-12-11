package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Targetuser;
import java.util.HashMap;
import java.util.Map;

public class TargetuserValidator {

    public static Map<String, String> validateTargetuser(Targetuser targetuser) {
        Map<String, String> errors = new HashMap<>();

        // Validate targetName
        if (targetuser.getTargetName() == null || targetuser.getTargetName().isEmpty()) {
            errors.put("targetName", "Tên nhóm khách hàng không được để trống");
        } else if (targetuser.getTargetName().length() < 3 || targetuser.getTargetName().length() > 100) {
            errors.put("targetName", "Tên nhóm khách hàng phải có độ dài từ 3 đến 100 ký tự");
        }

        // Validate description (if provided, should be between 5 and 500 characters)
        if (targetuser.getDescription() != null && (targetuser.getDescription().length() < 5 || targetuser.getDescription().length() > 500)) {
            errors.put("description", "Mô tả phải có độ dài từ 5 đến 500 ký tự");
        }

        return errors;
    }
}
