package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.VoucherDto;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.VoucherService;
import com.example.TheGioiSua_2024.service.logService;
import com.example.TheGioiSua_2024.util.VoucherValidator;
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
    // Step 1: Validate voucher using VoucherValidator
    Map<String, String> errors = VoucherValidator.validateVoucher(voucher);

    // Step 2: If there are validation errors, return a 400 response with error details
    if (!errors.isEmpty()) {
      List<Map<String, String>> errorList = new ArrayList<>();
      // Convert the errors to a list of error objects with field and message
      errors.forEach((field, message) -> {
        Map<String, String> error = Map.of(
                "field", field,
                "message", message
        );
        errorList.add(error);
      });

      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    String checkDuplicateMessage = voucherService.checkDuplicatevoucher(voucher.getVouchercode());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "vouchercode",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    // Step 3: Retrieve the token from the request header
    String token = jwtUtilities.getToken(request);

    // Step 4: Save the voucher and return success response
    String message = voucherService.saveVoucher(token, voucher);

    // Step 5: Check if the message contains any error (e.g., duplicate voucher, invalid date)
    if (message.contains("Voucher với mã này đã tồn tại") || message.contains("Ngày Bắt Đầu Phải Lớn Hơn Hoặc Bằng Ngày Hiện Tại")) {
      return ResponseEntity.badRequest().body(Map.of("status", "error", "message", message));
    }

    // Step 6: If everything is successful, return a success response
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }

  //http://localhost:1234/api/Voucher/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@NonNull HttpServletRequest request, @PathVariable("id") Long id, @RequestBody Voucher voucher) {
    // Validate voucher using VoucherValidator
    Map<String, String> errors = VoucherValidator.validateVoucher(voucher);

    // If there are validation errors, return a 400 response with error details
    if (!errors.isEmpty()) {
      List<Map<String, String>> errorList = new ArrayList<>();
      // Convert the errors to a list of error objects with field and message
      errors.forEach((field, message) -> {
        Map<String, String> error = Map.of(
                "field", field,
                "message", message
        );
        errorList.add(error);
      });

      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }

    String token = jwtUtilities.getToken(request);
    return ResponseEntity.ok(Map.of("status", "success", "message", voucherService.updateVoucher(token, id, voucher)));
  }

  //http://localhost:1234/api/Voucher/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@NonNull HttpServletRequest request, @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    String message = voucherService.deleteVoucher(token, id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
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
}
