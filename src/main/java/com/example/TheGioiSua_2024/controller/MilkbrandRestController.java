package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.MilkbrandService;
import com.example.TheGioiSua_2024.service.MilkdetailService;
import com.example.TheGioiSua_2024.util.MilkbrandValidator;
import com.example.TheGioiSua_2024.util.VoucherValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/Milkbrand")
public class MilkbrandRestController {

  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private MilkbrandService milkbrandService;
  @Autowired
  private MilkdetailService milkdetailService;

  //http://localhost:1234/api/Milkbrand/lst
  @GetMapping("/lst")
  public List<Milkbrand> lst() {
    return milkbrandService.getAllMilkbrands();
  }

  @GetMapping("/lst/{id}")
  public Milkbrand getMilkbrandById(@PathVariable Long id) {
    return milkbrandService.getMilkbrandById(id);
  }

  //http://localhost:1234/api/Milkbrand/add
  @PostMapping("/add")
  public ResponseEntity<?> add(@NonNull HttpServletRequest request,
    @RequestBody  Milkbrand milkbrand
    ) {

    Map<String, String> errors = MilkbrandValidator.validateMilkbrand(milkbrand);

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
    String checkDuplicateMessage = milkbrandService.checkDuplicatemilkbrand(milkbrand.getMilkbrandname());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "milkbrandname",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    // Step 3: Retrieve the token from the request header
    String token = jwtUtilities.getToken(request);
    String resultMessage = milkbrandService.addMilkbrand(token, milkbrand);
    return ResponseEntity.ok(Map.of("status", "success", "message", resultMessage));
  }

  //http://localhost:1234/api/Milkbrand/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@NonNull HttpServletRequest request, @PathVariable Long id,
    @RequestBody  Milkbrand milkbrand) {
    String token = jwtUtilities.getToken(request);
    Map<String, String> errors = MilkbrandValidator.validateMilkbrand(milkbrand);
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
    String checkDuplicateMessage = milkbrandService.checkDuplicatemilkbrand(milkbrand.getMilkbrandname());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "milkbrandname",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    return ResponseEntity.ok(
      Map.of("status", "success", "message",
        milkbrandService.updateMilkbrand(token, id, milkbrand)));
  }

  //http://localhost:1234/api/Milkbrand/delete/{id}
  @DeleteMapping("/delete/{id}") // Change to DELETE method
  public ResponseEntity<?> delete(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    String message = milkbrandService.deleteMilkbrand(token, id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }

  // http://localhost:1234/api/Milkdetail/getMilkDetailPage

  @GetMapping("/getMilkBrandPage")
  public Page<Milkbrand> getMilkBrandPage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return milkbrandService.getMilkbrandPage(pageable);
  }
}
