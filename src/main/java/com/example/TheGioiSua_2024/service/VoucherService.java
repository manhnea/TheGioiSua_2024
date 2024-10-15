package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.service.impl.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoucherService implements IVoucherService {
@Autowired
private VoucherRepository voucherRepository;
    @Override
    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }

    @Override
    public String  saveVoucher(Voucher voucher) {
        String trimmedName = voucher.getVouchercode().trim();
        voucher.setVouchercode(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Voucher> existingContainer = getVoucherByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Container với tên này đã tồn tại.";
        }
        voucher.setStatus(1);
        voucherRepository.save(voucher);
        return "Voucher saved";
    }

    @Override
    public String updateVoucher(Long id, Voucher voucher) {
        String trimmedName = voucher.getVouchercode().trim();
        voucher.setVouchercode(trimmedName);
        // Kiểm tra xem tên  đã tồn tại chưa
        Optional<Voucher> existingContainer = getVoucherByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Container với tên này đã tồn tại.";
        }
       Voucher voucher1 = voucherRepository.findById(id).orElseThrow();
       voucher1.setDiscountpercentage(voucher.getDiscountpercentage());
       voucher1.setMaxamount(voucher.getMaxamount());
       voucher1.setEnddate(voucher.getEnddate());
       voucher1.setUsagecount(voucher.getUsagecount());
       voucher1.setStartdate(voucher.getStartdate());
       voucher1.setVouchercode(voucher.getVouchercode());
      voucherRepository.save(voucher1);
      return "Voucher updated";
    }

    @Override
    public Voucher deleteVoucher(Long id) {
        Voucher voucher1 = voucherRepository.findById(id).orElseThrow();
        voucher1.setStatus(0);
        return voucherRepository.save(voucher1);
    }

    @Override
    public Optional<Voucher> getVoucherByName(String voucherName) {
        return voucherRepository.findByVoucher(voucherName);
    }
}
