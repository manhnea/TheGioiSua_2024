package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.entity.Voucher;

import java.util.List;
import java.util.Optional;

public interface IVoucherService {
    List<Voucher> getVoucherList();
    String saveVoucher(Voucher voucher);
    String updateVoucher(Long id, Voucher voucher);
    Voucher deleteVoucher(Long id);
    Optional<Voucher> getVoucherByName(String voucherName);

    Voucher getVoucherById(Long id);
}
