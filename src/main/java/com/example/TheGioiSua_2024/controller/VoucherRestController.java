package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Voucher")
public class VoucherRestController {
    @Autowired
    private VoucherService voucherService;
    @GetMapping("/lst")
    public List<Voucher> lst(){
        return voucherService.getVoucherList();
    }
    @PostMapping("/add")
    public Voucher add(@RequestBody Voucher voucher){
        return voucherService.saveVoucher(voucher);
    }
    @PutMapping("/update/{id}")
    public Voucher update(@PathVariable("id") Long id, @RequestBody Voucher voucher){
        return voucherService.updateVoucher(id,voucher);
    }
    @PutMapping("/delete/{id}")
    public Voucher delete(@PathVariable("id") Long id){
        return voucherService.deleteVoucher(id);
    }
}
