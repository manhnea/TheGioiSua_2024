package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.PackagingunitService;
import com.example.TheGioiSua_2024.util.PackagingUnitValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Packagingunit")
public class PackagingunitRestController {

  @Autowired
  private PackagingunitService packagingunitService;
  @Autowired
  private JwtUtilities jwtUtilities;

  //http://localhost:1234/api/Packagingunit/lst
  @GetMapping("/lst")
  public List<Packagingunit> getAllPackagingunit() {
    return packagingunitService.getAllPackagingunit();
  }

  @GetMapping("/lst/{id}")
  public Packagingunit getPackagingunit(@PathVariable("id") Long id) {
    return packagingunitService.getPackagingunitById(id);
  }

  //http://localhost:1234/api/Packagingunit/add
  @PostMapping("/add")
  public ResponseEntity<?> addPackagingunit(@NonNull HttpServletRequest request,
    @RequestBody Packagingunit packagingunit) {
    Map<String, String> errors = PackagingUnitValidator.validatePackagingUnit(packagingunit);

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
    String token = jwtUtilities.getToken(request);
    return ResponseEntity.ok(Map.of("status", "success", "message",
      packagingunitService.addPackagingunit(token, packagingunit)));
  }

  //http://localhost:1234/api/Packagingunit/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> updatePackagingunit(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id,
    @RequestBody  Packagingunit packagingunit) {
    Map<String, String> errors = PackagingUnitValidator.validatePackagingUnit(packagingunit);

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
    String token = jwtUtilities.getToken(request);
    return ResponseEntity.ok(Map.of("status", "success", "message",
      packagingunitService.updatePackagingunit(token, id, packagingunit)));
  }

  //http://localhost:1234/api/Packagingunit/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deletePackagingunit(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    String message = packagingunitService.deletePackagingunit(token, id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }

  @GetMapping("/getPackagingunitPage")
  public Page<Packagingunit> getPackagingunitPage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return packagingunitService.getPackagingunitPage(pageable);
  }
}
