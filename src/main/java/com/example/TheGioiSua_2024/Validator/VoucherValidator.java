package com.example.TheGioiSua_2024.Validator;

import com.example.TheGioiSua_2024.entity.Voucher;

public class VoucherValidator {

    public static  String validateVoucher(Voucher voucher) {


        // Validate vouchercode
        if (voucher.getVouchercode() == null || voucher.getVouchercode().isEmpty()) {
            return "Mã voucher không được để trống";
        } else if (!voucher.getVouchercode().matches("^[A-Za-z0-9]+$")) {
            return "Mã voucher chỉ được chứa các ký tự chữ và số (A-Z, a-z, 0-9), không được chứa khoảng trắng";
        } else if (voucher.getVouchercode().length() < 5 || voucher.getVouchercode().length() > 20) {
            return"Mã voucher phải từ 5 đến 20 ký tự";
        }

        // Validate startdate
        if (voucher.getStartdate() == null || voucher.getStartdate().isBefore(java.time.LocalDate.now())) {
            return "Ngày bắt đầu phải là hôm nay hoặc một ngày trong tương lai";
        }

        // Validate enddate
        if (voucher.getEnddate() == null || voucher.getEnddate().isBefore(java.time.LocalDate.now())) {
            return "Ngày kết thúc phải là một ngày trong tương lai";
        }

        // Validate discount percentage
        if (voucher.getDiscountpercentage() < 1 || voucher.getDiscountpercentage() > 100) {
            return "Phần trăm giảm giá phải từ 1 đến 100";
        }

        // Validate maxamount
        if (voucher.getMaxamount() < 0) {
            return "Số tiền tối đa phải lớn hơn hoặc bằng 0";
        }

        // Validate usagecount
        if (voucher.getUsagecount() < 0) {
            return "Số lần sử dụng phải lớn hơn hoặc bằng 0";
        }

        // Validate minamount
        if (voucher.getMinamount() < 0) {
            return "Số tiền tối thiểu phải lớn hơn hoặc bằng 0";
        }

        // Validate status
        if (voucher.getStatus() != 0 && voucher.getStatus() != 1) {
           return  "Trạng thái không hợp lệ (chỉ chấp nhận 0 hoặc 1)";
        }
        if(voucher.getMaxamount()>(voucher.getMinamount()/2)){
            System.out.println("Số tiền tối đa phải nhỏ hơn" + voucher.getMinamount()/2);
            return "Số tiền tối đa phải nhỏ hơn" + voucher.getMinamount()/2;
        }
        return null;
    }
}
