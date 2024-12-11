package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.entity.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ProductValidator {

    // Regex pattern for validating URLs (basic version)
    private static final String URL_REGEX = "^(http|https)://[^\\s]+$";

    public static Map<String, String> validateProduct(Product product) {
        Map<String, String> errors = new HashMap<>();

        // Validate productname
        if (product.getProductname() == null || product.getProductname().isEmpty()) {
            errors.put("productname", "Tên sản phẩm không được để trống");
        } else if (product.getProductname().length() < 3 || product.getProductname().length() > 100) {
            errors.put("productname", "Tên sản phẩm phải có độ dài từ 3 đến 100 ký tự");
        } else if (!product.getProductname().matches("^[A-Za-z0-9 ]+$")) {
            errors.put("productname", "Tên sản phẩm chỉ được chứa các ký tự chữ, số và khoảng trắng");
        }

        // Validate productCode
        if (product.getProductCode() == null || product.getProductCode().isEmpty()) {
            errors.put("productCode", "Mã sản phẩm không được để trống");
        } else if (product.getProductCode().length() < 5 || product.getProductCode().length() > 50) {
            errors.put("productCode", "Mã sản phẩm phải có độ dài từ 5 đến 50 ký tự");
        } else if (!product.getProductCode().matches("^[A-Za-z0-9]+$")) {
            errors.put("productCode", "Mã sản phẩm chỉ được chứa các ký tự chữ và số");
        }

        // Validate milkType (should not be null as it's required)
        if (product.getMilkType() == null) {
            errors.put("milkType", "Loại sữa không được để trống");
        }

        // Validate milkBrand (should not be null as it's required)
        if (product.getMilkBrand() == null) {
            errors.put("milkBrand", "Thương hiệu sữa không được để trống");
        }

        // Validate targetUser (should not be null as it's required)
        if (product.getTargetUser() == null) {
            errors.put("targetUser", "Nhóm khách hàng không được để trống");
        }

        // Validate productUrl (if not null, must be a valid URL)
        if (product.getProductUrl() != null && !Pattern.matches(URL_REGEX, product.getProductUrl())) {
            errors.put("productUrl", "Địa chỉ URL sản phẩm không hợp lệ");
        }

        // Validate imgUrl (if not null, must be a valid URL)
        if (product.getImgUrl() != null && !Pattern.matches(URL_REGEX, product.getImgUrl())) {
            errors.put("imgUrl", "Địa chỉ URL hình ảnh không hợp lệ");
        }

        return errors;
    }
}
