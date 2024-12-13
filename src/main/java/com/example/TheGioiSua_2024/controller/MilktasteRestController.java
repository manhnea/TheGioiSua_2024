package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.MilktasteService;
import com.example.TheGioiSua_2024.util.MilkbrandValidator;
import com.example.TheGioiSua_2024.util.MilktasteValidator;
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
@RequestMapping("/Milktaste")
public class MilktasteRestController {

  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private MilktasteService milktasteService;

  //http://localhost:1234/api/Milktaste/lst
  @GetMapping("/lst")
  public List<Milktaste> lst() {
    return milktasteService.getAllMilktaste();
  }

  @GetMapping("/lst/{id}")
  public Milktaste lst(@PathVariable("id") Long id) {
    return milktasteService.getMilktasteById(id);
  }

  //http://localhost:1234/api/Milktaste/add
  @PostMapping("/add")
  public ResponseEntity<?> add(@NonNull HttpServletRequest request,
    @RequestBody  Milktaste milktaste) {
    Map<String, String> errors = MilktasteValidator.validateMilktaste(milktaste);

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
    String checkDuplicateMessage = milktasteService.checkDuplicatemilktaste(milktaste.getMilktastename());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "milktastename",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    String token = jwtUtilities.getToken(request);
    return ResponseEntity.ok(
      Map.of("status", "success", "message", milktasteService.addMilktaste(token, milktaste)));
  }

  //http://localhost:1234/api/Milktaste/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id,
    @RequestBody  Milktaste milktaste) {
    Map<String, String> errors = MilktasteValidator.validateMilktaste(milktaste);

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
    String checkDuplicateMessage = milktasteService.checkDuplicatemilktaste(milktaste.getMilktastename());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "milktastename",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    return ResponseEntity.ok(
      Map.of("status", "success", "message", milktasteService.updateMilktaste(id, milktaste)));
  }

  //http://localhost:1234/api/Milktaste/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    String message = milktasteService.deleteMilktaste(token, id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }

  @GetMapping("/getMilktastePage")
  public Page<Milktaste> getMilktastePage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return milktasteService.getMilktastePage(pageable);
  }

  @GetMapping("/getMilktastePageByName")
    public Page<Milktaste> getMilktastePageByName(@RequestParam("page") int page,
        @RequestParam("size") int size, @RequestParam("milktasteName") String milktasteName) {
        Pageable pageable = PageRequest.of(page, size);
        return milktasteService.getMilktastePageByName(milktasteName, pageable);
    }

}
