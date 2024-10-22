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

    @GetMapping("/lst/{id}")
    private Milkdetail getMilkdetailById(@PathVariable Long id) {
        return milkdetailService.getById(id);
    }

    //http://localhost:1234/api/Milkdetail/add
    @PostMapping("/add")
    private ResponseEntity<?> add(@RequestBody @Valid Milkdetail milkdetail, BindingResult bindingResult) {
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

        return ResponseEntity.ok(Map.of("status", "success", "message", milkdetailService.add(milkdetail)));
    }

    //http://localhost:1234/api/Milkdetail/update/{id}
    @PutMapping("/update/{id}")
    private ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Milkdetail milkdetail, BindingResult bindingResult) {
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
        return ResponseEntity.ok(Map.of("status", "success", "message", milkdetailService.update(id, milkdetail)));
    }

    //http://localhost:1234/api/Milkdetail/delete/{id}
    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable("id") Long id) {
        String message = milkdetailService.delete(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", message));
    }

    //http://localhost:1234/api/Milkdetail/page
    @GetMapping("/page")
    private ResponseEntity<?> getPageMilkDetail(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody MilkDetailDto milkDetailDto) {
        if (milkDetailDto.getMilktypeID() == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "MilktypeID NULL"));
        }
        if (milkDetailDto.getMilkBrandID() == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "MilkBrandID NULL"));
        }
        if (milkDetailDto.getPackagingunitID() == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "PackagingunitID NULL"));
        }
        if (milkDetailDto.getMilktasteID() == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "MilktasteID NULL"));
        }
        if (milkDetailDto.getProductID() == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "ProductID NULL"));
        }
        if (milkDetailDto.getTargetuserID() == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "TargetuserID NULL"));
        }
        if (milkDetailDto.getUsagecapacityID() == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "UsagecapacityID NULL"));
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<MilkDetailDto> pageMilkDetail = milkdetailService.getPageMilkDetail(pageable, milkDetailDto);
        if (pageMilkDetail.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "Danh Sách Trống"));
        }
        return ResponseEntity.ok(Map.of("status", "success", "message", pageMilkDetail));
    }
    //http://localhost:1234/api/Milkdetail/getMilkDetail

    @GetMapping("/getMilkDetail")
//    private ResponseEntity<?> getMilkDetail(@RequestBody MilkDetailDto milkDetailDto) {
        public ResponseEntity<?> getMilkDetail(
            @RequestParam Long milktypeID,
            @RequestParam Long milkBrandID,
            @RequestParam Long packagingunitID,
            @RequestParam Long milktasteID,
            @RequestParam Long productID,
            @RequestParam Long targetuserID,
            @RequestParam Long usagecapacityID) {
//        if (milkDetailDto == null) {
//            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "RequestBody NULL"));
//        }
//        if (milkDetailDto.getMilktypeID() == null) {
//            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "MilktypeID NULL"));
//        }
//        if (milkDetailDto.getMilkBrandID() == null) {
//            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "MilkBrandID NULL"));
//        }
//        if (milkDetailDto.getPackagingunitID() == null) {
//            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "PackagingunitID NULL"));
//        }
//        if (milkDetailDto.getMilktasteID() == null) {
//            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "MilktasteID NULL"));
//        }
//        if (milkDetailDto.getProductID() == null) {
//            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "ProductID NULL"));
//        }
//        if (milkDetailDto.getTargetuserID() == null) {
//            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "TargetuserID NULL"));
//        }
//        if (milkDetailDto.getUsagecapacityID() == null) {
//            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "UsagecapacityID NULL"));
//        }
        
//        MilkDetailDto milkDetail = milkdetailService.getMilkDetail(milkDetailDto);
          MilkDetailDto milkDetail = milkdetailService.getMilkDetail(milktypeID, milkBrandID, packagingunitID, milktasteID, productID, targetuserID, usagecapacityID);

        if (milkDetail == null) {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", "Danh Sách Trống"));
        }
        return ResponseEntity.ok(Map.of("status", "success", "message", milkDetail));
    }
}
