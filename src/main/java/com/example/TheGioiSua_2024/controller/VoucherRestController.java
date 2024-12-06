package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.VoucherDto;
import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.LogRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.VoucherService;
import com.example.TheGioiSua_2024.service.logService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  @GetMapping("/lst/{id}")
  public Voucher get(@PathVariable("id") Long id) {
    return voucherService.getVoucherById(id);
  }

  @PostMapping("/add")
  public ResponseEntity<?> add(@NonNull HttpServletRequest request,
      @RequestBody @Valid Voucher voucher, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<Map<String, String>> errors = new ArrayList<>();
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        Map<String, String> error = new HashMap<>();
        error.put("field", fieldError.getField());
        error.put("message", fieldError.getDefaultMessage());
        errors.add(error);
      }
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
    }
    String token = jwtUtilities.getToken(request);
    return ResponseEntity.ok(
        Map.of("status", "success", "message", voucherService.saveVoucher(token, voucher)));
  }

  //http://localhost:1234/api/Voucher/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@NonNull HttpServletRequest request, @PathVariable("id") Long id,
      @RequestBody @Valid Voucher voucher,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      List<Map<String, String>> errors = new ArrayList<>();
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        Map<String, String> error = new HashMap<>();
        error.put("field", fieldError.getField());
        error.put("message", fieldError.getDefaultMessage());
        errors.add(error);
      }
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
    }
    String token = jwtUtilities.getToken(request);
    return ResponseEntity.ok(
        Map.of("status", "success", "message", voucherService.updateVoucher(token, id, voucher)));

  }

  //http://localhost:1234/api/Voucher/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@NonNull HttpServletRequest request,
      @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);

    String message = voucherService.deleteVoucher(token, id);

    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }

  @GetMapping("/voucercode")
  public ResponseEntity<?> discountmoney(@NonNull HttpServletRequest request,
      @RequestParam("vouchercode") String vouchercode,
      @RequestParam("total") int total
  ) {
    VoucherDto voucherDto = new VoucherDto();
    String token = jwtUtilities.getToken(request);

    voucherDto.setVouchercode(vouchercode);
    voucherDto.setTotal(total);
    return voucherService.discountmoney(token, voucherDto);
  }
  @GetMapping("/voucherActive")
  public ResponseEntity<?> voucherActive(){
      return voucherService.voucherActive();
  }
}
