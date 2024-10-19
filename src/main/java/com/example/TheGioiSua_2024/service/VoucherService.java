package com.example.TheGioiSua_2024.service;

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
    public String saveVoucher(Voucher voucher) {
        // Loại bỏ khoảng trắng ở đầu và cuối mã voucher
        String trimmedName = voucher.getVouchercode().trim();
        voucher.setVouchercode(trimmedName);
        // Kiểm tra xem mã voucher đã tồn tại chưa
        Optional<Voucher> existingVoucher = getVoucherByName(trimmedName);
        if (existingVoucher.isPresent()) {
            return "Voucher với mã này đã tồn tại.";
        }
        voucher.setStatus(1); // Kích hoạt voucher
        voucherRepository.save(voucher);
        return "Đã lưu voucher thành công.";
    }

    @Override
    public String updateVoucher(Long id, Voucher voucher) {
        Voucher existingVoucher = voucherRepository.findById(id).orElseThrow();
        String currentVoucherCode = existingVoucher.getVouchercode();

        // Nếu mã voucher không thay đổi
        if (currentVoucherCode.equals(voucher.getVouchercode())) {
            existingVoucher.setDiscountpercentage(voucher.getDiscountpercentage());
            existingVoucher.setMaxamount(voucher.getMaxamount());
            existingVoucher.setEnddate(voucher.getEnddate());
            existingVoucher.setUsagecount(voucher.getUsagecount());
            existingVoucher.setStartdate(voucher.getStartdate());
            voucherRepository.save(existingVoucher);
            return "Đã cập nhật voucher thành công.";
        }
        // Nếu mã voucher thay đổi, kiểm tra xem mã mới đã tồn tại chưa
        else if (voucherRepository.findByVoucher(voucher.getVouchercode()).isPresent()) {
            return "Mã voucher này đã tồn tại.";
        }

        existingVoucher.setDiscountpercentage(voucher.getDiscountpercentage());
        existingVoucher.setMaxamount(voucher.getMaxamount());
        existingVoucher.setEnddate(voucher.getEnddate());
        existingVoucher.setUsagecount(voucher.getUsagecount());
        existingVoucher.setStartdate(voucher.getStartdate());
        existingVoucher.setVouchercode(voucher.getVouchercode());
        voucherRepository.save(existingVoucher);
        return "Đã cập nhật voucher thành công.";
    }

    @Override
    public String deleteVoucher(Long id) {
        Voucher existingVoucher = voucherRepository.findById(id).orElseThrow();
       if (existingVoucher.getStatus() == 0) {
            existingVoucher.setStatus(1);
            voucherRepository.save(existingVoucher);
            return "Khôi phục voucher thành công.";
        } else {
            existingVoucher.setStatus(0);
            voucherRepository.save(existingVoucher);
            return "Đã xóa voucher thành công.";
        }
    }

    @Override
    public Optional<Voucher> getVoucherByName(String voucherName) {
        return voucherRepository.findByVoucher(voucherName);
    }

    @Override
    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id).orElseThrow();
    }
}
