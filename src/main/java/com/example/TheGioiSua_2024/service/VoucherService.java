package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.service.impl.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService implements IVoucherService {
@Autowired
private VoucherRepository voucherRepository;
    @Override
    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        voucher.setStatus(1);
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher updateVoucher(Long id, Voucher voucher) {
       Voucher voucher1 = voucherRepository.findById(id).orElseThrow();
       voucher1.setDiscountpercentage(voucher.getDiscountpercentage());
       voucher1.setExchangeamount(voucher.getExchangeamount());
       voucher1.setEnddate(voucher.getEnddate());
       voucher1.setUsagecount(voucher.getUsagecount());
       voucher1.setStartdate(voucher.getStartdate());
       voucher1.setVouchercode(voucher.getVouchercode());
      return voucherRepository.save(voucher1);
    }

    @Override
    public Voucher deleteVoucher(Long id) {
        Voucher voucher1 = voucherRepository.findById(id).orElseThrow();
        voucher1.setStatus(0);
        return voucherRepository.save(voucher1);
    }
}
