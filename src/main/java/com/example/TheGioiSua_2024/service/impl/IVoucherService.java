package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.VoucherDto;
import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.entity.Voucher;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IVoucherService {

  List<Voucher> getVoucherList();

  String checkDuplicatevoucher(String voucher);

  String saveVoucher(String token, Voucher voucher);

  String updateVoucher(String token, Long id, Voucher voucher);

  String deleteVoucher(String token, Long id);

  Optional<Voucher> getVoucherByName(String voucherName);

  Voucher getVoucherById(Long id);

  ResponseEntity<?> discountmoney(String token, VoucherDto voucherDto);
  ResponseEntity<?> voucherActive();
  Page<Voucher> getVoucherPage(Pageable pageable);

}
