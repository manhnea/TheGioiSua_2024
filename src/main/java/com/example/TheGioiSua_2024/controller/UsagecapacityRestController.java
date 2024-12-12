package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.UsagecapacityService;
import com.example.TheGioiSua_2024.util.TargetuserValidator;
import com.example.TheGioiSua_2024.util.UsagecapacityValidator;
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
@RequestMapping("/Usagecapacity")
public class UsagecapacityRestController {

  @Autowired
  private UsagecapacityService usagecapacityService;
  @Autowired
  private JwtUtilities jwtUtilities;

  //http://localhost:1234/api/Usagecapacity/lst
  @GetMapping("/lst")
  public List<Usagecapacity> getUsagecapacity() {
    return usagecapacityService.getAllUsagecapacity();
  }

  //http://localhost:1234/api/Usagecapacity/add
  @PostMapping("/add")
  public ResponseEntity<?> addUsagecapacity(@NonNull HttpServletRequest request,
      @RequestBody @Valid Usagecapacity usagecapacity, BindingResult bindingResult) {
    Map<String, String> errors = UsagecapacityValidator.validateUsagecapacity(usagecapacity);

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
        usagecapacityService.addUsagecapacity(token, usagecapacity)));
  }

  @GetMapping("/lst/{id}")
  public Usagecapacity getUsagecapacity(@PathVariable("id") Long id) {
    return usagecapacityService.getUsagecapacityById(id);
  }

  //http://localhost:1234/api/Usagecapacity/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateUsagecapacity(@NonNull HttpServletRequest request,
      @PathVariable("id") Long id,
      @RequestBody  Usagecapacity usagecapacity) {
    Map<String, String> errors = UsagecapacityValidator.validateUsagecapacity(usagecapacity);

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
        usagecapacityService.updateUsagecapacity(token, id, usagecapacity)));
  }

  //http://localhost:1234/api/Usagecapacity/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteUsagecapacity(@NonNull HttpServletRequest request,
      @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    String message = usagecapacityService.deleteUsagecapacity(token, id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }
  @GetMapping("/getUsagecapacityPage")
  public Page<Usagecapacity> getUsagecapacityPage(@RequestParam("page") int page, @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return usagecapacityService.getUsagecapacityPage(pageable);
  }
}
