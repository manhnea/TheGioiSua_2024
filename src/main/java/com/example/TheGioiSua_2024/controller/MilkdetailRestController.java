package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.service.MilkdetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@CrossOrigin
@RestController
@RequestMapping("/Milkdetail")
public class MilkdetailRestController {

  @Autowired
  private MilkdetailService milkdetailService;

  //http://localhost:1234/api/Milkdetail/lst
  @GetMapping("/lst")
  private List<Milkdetail> lst() {
    return milkdetailService.getAll();
  }

  // http://localhost:1234/api/Milkdetail/update-stock/{id}
  @PutMapping("/update-stock/{id}")
  public ResponseEntity<?> updateStockQuantity(
      @PathVariable Long id,
      @RequestParam int quantity) {
    try {
      String message = milkdetailService.updateStockQuantity(id, quantity);
      return ResponseEntity.ok(Map.of("status", "success", "message", message));
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest()
          .body(Map.of("status", "error", "message", e.getMessage()));
    }
  }

  @GetMapping("/lst/{id}")
  private Milkdetail getMilkdetailById(@PathVariable Long id) {
    return milkdetailService.getById(id);
  }

  //http://localhost:1234/api/Milkdetail/add
  @PostMapping("/add")
  private ResponseEntity<?> add(@RequestBody @Valid Milkdetail milkdetail,
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

    return ResponseEntity.ok(
        Map.of("status", "success", "message", milkdetailService.add(milkdetail)));
  }

  //http://localhost:1234/api/Milkdetail/update/{id}
  @PutMapping("/update/{id}")
  private ResponseEntity<?> update(@PathVariable("id") Long id,
      @Valid @RequestBody Milkdetail milkdetail, BindingResult bindingResult) {
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
    return ResponseEntity.ok(
        Map.of("status", "success", "message", milkdetailService.update(id, milkdetail)));
  }

  //http://localhost:1234/api/Milkdetail/delete/{id}
  @DeleteMapping("/delete/{id}")
  private ResponseEntity<?> delete(@PathVariable("id") Long id) {
    String message = milkdetailService.delete(id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }
  //http://localhost:1234/api/Milkdetail/getMilkDetail

  @GetMapping("/getMilkDetail")
  public ResponseEntity<?> getMilkDetail(
      @RequestParam Long packagingunitID,
      @RequestParam Long milktasteID,
      @RequestParam Long productID,
      @RequestParam Long usagecapacityID) {
    MilkDetailDto milkDetail = milkdetailService.getMilkDetail(packagingunitID, milktasteID,
        productID, usagecapacityID);

    if (milkDetail == null) {
      return ResponseEntity.badRequest()
          .body(Map.of("status", "error", "errors", "Danh Sách Trống"));
    }
    return ResponseEntity.ok(Map.of("status", "success", "message", milkDetail));
  }

  @GetMapping("/getMilkDetailPage")
  public ResponseEntity<?> getMilkDetailPage(
      @RequestParam int page,
      @RequestParam int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Milkdetail> milkDetailPage = milkdetailService.getMilkDetailPage(pageable);

    if (milkDetailPage.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(Map.of("status", "error", "errors", "Danh Sách Trống"));
    }
    return ResponseEntity.ok(Map.of("status", "success", "message", milkDetailPage));
  }

  @GetMapping("/search")
  public Page<Milkdetail> search(
      @RequestParam(required = false) String productname,
      @RequestParam int page,
      @RequestParam int size) {

    Pageable pageable = PageRequest.of(page, size);

    return milkdetailService.getMilkDetailsearch(
        productname != null ? productname : "",
        pageable
    );
  }

  @GetMapping("/low-stock-count")
  public long getLowStockCount() {
    return milkdetailService.countLowStockMilkDetails();
  }

  @GetMapping("/count-milkdetail")
  public long getcountmilkdetail() {
    return milkdetailService.countMilkDetails();
  }
  @GetMapping("/more")
  private List<MilkDetailDto> hethang() {
    return milkdetailService.gethethang();
  }

}
