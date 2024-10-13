package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/Voucher")
public class VoucherRestController {
    @Autowired
    private VoucherService voucherService;
    //http://localhost:1234/api/Voucher/lst
    @GetMapping("/lst")
    public List<Voucher> lst(){
        return voucherService.getVoucherList();
    }
    //http://localhost:1234/api/Voucher/add
    @PostMapping("/add")
    public Voucher add(@RequestBody Voucher voucher){
        return voucherService.saveVoucher(voucher);
    }
    //http://localhost:1234/api/Voucher/update/{id}
    @PutMapping("/update/{id}")
    public Voucher update(@PathVariable("id") Long id, @RequestBody Voucher voucher){
        return voucherService.updateVoucher(id,voucher);
    }
    //http://localhost:1234/api/Voucher/delete/{id}
    @PutMapping("/delete/{id}")
    public Voucher delete(@PathVariable("id") Long id){
        return voucherService.deleteVoucher(id);
    }
}
