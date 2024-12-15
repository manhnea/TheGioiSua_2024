package com.example.TheGioiSua_2024.Validator;

import com.example.TheGioiSua_2024.entity.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ProductValidator {

    // Regex pattern for validating URLs (basic version)
    private static final String URL_REGEX = "^(http|https)://[^\\s]+$";

    public static  String validateProduct(Product product) {


        // Validate productname
        if (product.getProductname() == null || product.getProductname().isEmpty()) {
            return  "Tên sản phẩm không được để trống";
        } else if (product.getProductname().length() < 3 || product.getProductname().length() > 100) {
            return  "Tên sản phẩm phải có độ dài từ 3 đến 100 ký tự";
        } else if (!product.getProductname().matches("^[\\p{L}0-9\\s,.-/]+$")) {
            return "Tên sản phẩm chỉ được chứa các ký tự chữ, số và khoảng trắng";
        }


        // Validate milkType (should not be null as it's required)
        if (product.getMilkType().getId() == null) {
            return "Loại sữa không được để trống";
        }

        // Validate milkBrand (should not be null as it's required)
        if (product.getMilkBrand().getId() == null) {
            return  "Thương hiệu sữa không được để trống";
        }

        // Validate targetUser (should not be null as it's required)
        if (product.getTargetUser().getId() == null) {
            return  "Nhóm khách hàng không được để trống";
        }

        // Validate productUrl (if not null, must be a valid URL)
        if (product.getProductUrl() != null && !Pattern.matches(URL_REGEX, product.getProductUrl())) {
            return  "Địa chỉ URL sản phẩm không hợp lệ";
        }

        // Validate imgUrl (if not null, must be a valid URL)
        if (product.getImgUrl() != null && !Pattern.matches(URL_REGEX, product.getImgUrl())) {
            return  "Địa chỉ URL hình ảnh không hợp lệ";
        }

        return null;
    }
}
