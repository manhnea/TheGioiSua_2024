package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class MilkdetailValidator {

    // Phương thức kiểm tra các trường dữ liệu của Milkdetail
    public static Map<String, String> validateMilkdetail(Milkdetail milkdetail) {
        Map<String, String> errors = new HashMap<>();

        // Kiểm tra shelflifeofmilk (chỉ cho phép chữ cái, chữ số, dấu phẩy, dấu chấm, dấu gạch ngang và khoảng trắng)
        if (milkdetail.getShelflifeofmilk() == null || milkdetail.getShelflifeofmilk().isEmpty()) {
            errors.put("shelflifeofmilk", "Thời gian sử dụng sữa không được để trống");
        } else if (milkdetail.getShelflifeofmilk().length() < 5 || milkdetail.getShelflifeofmilk().length() > 50) {
            errors.put("shelflifeofmilk", "Thời gian sử dụng sữa phải có độ dài từ 5 đến 50 ký tự");
        } else if (!milkdetail.getShelflifeofmilk().matches("^[A-Za-z0-9àáảãạăắằẳẵặâấầẩẫậêếềểễệôốồổỗộơớờởỡợuúùủũụưứừửữự,.-\\s]+$")) {
            errors.put("shelflifeofmilk", "Thời gian sử dụng sữa chỉ được chứa chữ cái, chữ số, dấu phẩy, dấu chấm, dấu gạch ngang và khoảng trắng");
        }

        // Kiểm tra giá sản phẩm (phải là giá trị dương hoặc bằng 0)
        if (milkdetail.getPrice() < 0) {
            errors.put("price", "Giá sản phẩm phải lớn hơn hoặc bằng 0");
        } else if (milkdetail.getPrice() != milkdetail.getPrice()) {  // Kiểm tra giá có phải là một số hợp lệ
            errors.put("price", "Giá sản phẩm phải là một số hợp lệ");
        }

        // Kiểm tra số lượng trong kho (phải là một số nguyên và không nhỏ hơn 0)
        if (milkdetail.getStockquantity() < 0) {
            errors.put("stockquantity", "Số lượng trong kho không thể nhỏ hơn 0");
        } else if (milkdetail.getStockquantity() != milkdetail.getStockquantity()) {  // Kiểm tra số lượng có phải là một số hợp lệ
            errors.put("stockquantity", "Số lượng trong kho phải là một số hợp lệ");
        }
        // Kiểm tra imgUrl (phải là một URL hợp lệ)
        if (milkdetail.getImgUrl() != null && !milkdetail.getImgUrl().matches("^(http|https)://.*")) {
            errors.put("imgUrl", "Địa chỉ URL của hình ảnh không hợp lệ");
        }

        // Kiểm tra product (không được null)
        if (milkdetail.getProduct() == null) {
            errors.put("product", "Sản phẩm không được để trống");
        }

        // Kiểm tra milkTaste (không được null)
        if (milkdetail.getMilkTaste() == null) {
            errors.put("milkTaste", "Hương vị sữa không được để trống");
        }

        // Kiểm tra usageCapacity (không được null)
        if (milkdetail.getUsageCapacity() == null) {
            errors.put("usageCapacity", "Dung tích sử dụng không được để trống");
        }

        return errors;
    }
}
