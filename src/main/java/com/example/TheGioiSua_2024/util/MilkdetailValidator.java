package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import java.util.HashMap;
import java.util.Map;

public class MilkdetailValidator {

    public static Map<String, String> validateMilkdetail(Milkdetail milkdetail) {
        Map<String, String> errors = new HashMap<>();

        // Validate milkdetailcode
        if (milkdetail.getMilkdetailcode() == null || milkdetail.getMilkdetailcode().isEmpty()) {
            errors.put("milkdetailcode", "Mã chi tiết sữa không được để trống");
        } else if (milkdetail.getMilkdetailcode().length() < 5 || milkdetail.getMilkdetailcode().length() > 20) {
            errors.put("milkdetailcode", "Mã chi tiết sữa phải có độ dài từ 5 đến 20 ký tự");
        } else if (!milkdetail.getMilkdetailcode().matches("^[A-Za-z0-9]+$")) {
            errors.put("milkdetailcode", "Mã chi tiết sữa chỉ được chứa các ký tự chữ và số");
        }

        // Validate product (sản phẩm không được để trống)
        if (milkdetail.getProduct() == null) {
            errors.put("product", "Sản phẩm không được để trống");
        }

        // Validate milktaste (vị sữa không được để trống)
        if (milkdetail.getMilkTaste() == null) {
            errors.put("milktaste", "Vị sữa không được để trống");
        }

        // Validate usageCapacity (dung tích sử dụng không được để trống)
        if (milkdetail.getUsageCapacity() == null) {
            errors.put("usageCapacity", "Dung tích sử dụng không được để trống");
        }

        // Validate shelflifeofmilk (thời gian sử dụng, chỉ chứa ký tự chữ, số và khoảng trắng)
        if (milkdetail.getShelflifeofmilk() == null || milkdetail.getShelflifeofmilk().isEmpty()) {
            errors.put("shelflifeofmilk", "Thời gian sử dụng không được để trống");
        } else if (!milkdetail.getShelflifeofmilk().matches("^[A-Za-z0-9 ]+$")) {
            errors.put("shelflifeofmilk", "Thời gian sử dụng chỉ được chứa các ký tự chữ, số và khoảng trắng");
        }

        // Validate price (giá sản phẩm phải > 0)
        if (milkdetail.getPrice() <= 0) {
            errors.put("price", "Giá sản phẩm phải lớn hơn 0");
        }

        // Validate imgUrl (URL ảnh phải hợp lệ nếu không trống)
        if (milkdetail.getImgUrl() != null && !milkdetail.getImgUrl().isEmpty()) {
            String urlPattern = "^(https?|ftp)://[^\s/$.?#].[^\s]*$";
            if (!milkdetail.getImgUrl().matches(urlPattern)) {
                errors.put("imgUrl", "Định dạng URL ảnh không hợp lệ");
            }
        }

        // Validate description (mô tả phải từ 10 đến 500 ký tự và phù hợp với pattern)
        if (milkdetail.getDescription() == null || milkdetail.getDescription().isEmpty()) {
            errors.put("description", "Mô tả không được để trống");
        } else if (milkdetail.getDescription().length() < 10 || milkdetail.getDescription().length() > 500) {
            errors.put("description", "Mô tả phải có độ dài từ 10 đến 500 ký tự");
        } else {
            // Pattern cho phép chữ cái, số và một số ký tự đặc biệt như ., ?, !
            String descriptionPattern = "^[A-Za-z0-9.,?! ]+$";
            if (!milkdetail.getDescription().matches(descriptionPattern)) {
                errors.put("description", "Mô tả chỉ được chứa các ký tự chữ, số, và các ký tự đặc biệt như ., ?, !");
            }
        }


        // Validate stockquantity (số lượng tồn kho phải >= 1)
        if (milkdetail.getStockquantity() < 1) {
            errors.put("stockquantity", "Số lượng tồn kho phải >= 1");
        }

        return errors;
    }
}
