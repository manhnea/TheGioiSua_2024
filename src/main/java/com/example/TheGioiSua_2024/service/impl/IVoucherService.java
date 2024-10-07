package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Voucher;

import java.util.List;

public interface IVoucherService {
    List<Voucher> getVoucherList();
    Voucher saveVoucher(Voucher voucher);
    Voucher updateVoucher(Long id, Voucher voucher);
    Voucher deleteVoucher(Long id);
}
