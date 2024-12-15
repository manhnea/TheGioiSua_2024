package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.VoucherDto;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.VoucherService;
import com.example.TheGioiSua_2024.service.logService;
import com.example.TheGioiSua_2024.Validator.VoucherValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Voucher")
public class VoucherRestController {

  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private VoucherService voucherService;
  @Autowired
  private logService logRepository;

  //http://localhost:1234/api/Voucher/lst
  @GetMapping("/lst")
  public List<Voucher> lst() {
    return voucherService.getVoucherList();
  }

  //http://localhost:1234/api/Voucher/add
  @PostMapping("/add")
  public ResponseEntity<?> add(@NonNull HttpServletRequest request, @RequestBody Voucher voucher) {


    String token = jwtUtilities.getToken(request);

   return voucherService.saveVoucher(token, voucher);
  }

  //http://localhost:1234/api/Voucher/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@NonNull HttpServletRequest request, @PathVariable("id") Long id, @RequestBody Voucher voucher) {
    String token = jwtUtilities.getToken(request);
   return voucherService.updateVoucher(token, id, voucher);
  }

  //http://localhost:1234/api/Voucher/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@NonNull HttpServletRequest request, @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
  return voucherService.deleteVoucher(token, id);
  }

  @GetMapping("/voucercode")
  public ResponseEntity<?> discountmoney(@NonNull HttpServletRequest request, @RequestParam("vouchercode") String vouchercode, @RequestParam("total") int total) {
    VoucherDto voucherDto = new VoucherDto();
    String token = jwtUtilities.getToken(request);
    voucherDto.setVouchercode(vouchercode);
    voucherDto.setTotal(total);
    return voucherService.discountmoney(token, voucherDto);
  }

  @GetMapping("/voucherActive")
  public ResponseEntity<?> voucherActive() {
    return voucherService.voucherActive();
  }

  @GetMapping("/voucherPage")
  public Page<Voucher> getVoucherPage(@RequestParam("page") int page, @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return voucherService.getVoucherPage(pageable);
  }
  @GetMapping("/getVoucherById/{id}")
    public Voucher getVoucherById(@PathVariable("id") Long id) {
        return voucherService.getVoucherById(id);
    }
}
