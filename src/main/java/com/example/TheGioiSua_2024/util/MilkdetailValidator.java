package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class MilkdetailValidator {

    // Phương thức kiểm tra các trường dữ liệu của Milkdetail
    public static String validateMilkdetail(Milkdetail milkdetail) {


        // Kiểm tra shelflifeofmilk (chỉ cho phép chữ cái, chữ số, dấu phẩy, dấu chấm, dấu gạch ngang và khoảng trắng)
        if (milkdetail.getShelflifeofmilk() == null || milkdetail.getShelflifeofmilk().isEmpty()) {
            return "Thời gian sử dụng sữa không được để trống";
        } else if (milkdetail.getShelflifeofmilk().length() < 5 || milkdetail.getShelflifeofmilk().length() > 50) {
           return  "Thời gian sử dụng sữa phải có độ dài từ 5 đến 50 ký tự";
        }else if (!milkdetail.getShelflifeofmilk().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            return "Hạn sử dụng chỉ được chứa chữ cái (tiếng Việt có dấu), chữ số, khoảng trắng, dấu phẩy, dấu chấm, dấu gạch ngang và dấu gạch chéo.";
        }


        // Kiểm tra giá sản phẩm (phải là giá trị dương hoặc bằng 0)
        if (milkdetail.getPrice() < 0) {
            return "Giá sản phẩm phải lớn hơn hoặc bằng 0";
        } else if (milkdetail.getPrice() != milkdetail.getPrice()) {  // Kiểm tra giá có phải là một số hợp lệ
            return "Giá sản phẩm phải là một số hợp lệ";
        }

        // Kiểm tra số lượng trong kho (phải là một số nguyên và không nhỏ hơn 0)
        if (milkdetail.getStockquantity() < 0) {
            return "Số lượng trong kho không thể nhỏ hơn 0";
        } else if (milkdetail.getStockquantity() != milkdetail.getStockquantity()) {  // Kiểm tra số lượng có phải là một số hợp lệ
            return "Số lượng trong kho phải là một số hợp lệ";
        }
        // Kiểm tra imgUrl (phải là một URL hợp lệ)
        if (milkdetail.getImgUrl() != null && !milkdetail.getImgUrl().matches("^(http|https)://.*")) {
            return "Địa chỉ URL của hình ảnh không hợp lệ";
        }

        // Kiểm tra product (không được null)
        if (milkdetail.getProduct() == null) {
            return "Sản phẩm không được để trống";
        }

        // Kiểm tra milkTaste (không được null)
        if (milkdetail.getMilkTaste() == null) {
            return "Hương vị sữa không được để trống";
        }

        // Kiểm tra usageCapacity (không được null)
        if (milkdetail.getUsageCapacity() == null) {
            return "Dung tích sử dụng không được để trống";
        }

        return null;
    }
}
