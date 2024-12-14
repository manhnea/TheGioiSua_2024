package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.MilktypeService;
import com.example.TheGioiSua_2024.util.MilkTypeValidator;
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
@RequestMapping("/Milktype")
public class MilktypeRestController {

  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private MilktypeService milktypeService;

  //http://localhost:1234/api/Milktype/lst
  @GetMapping("/lst")
  public List<MilkType> getAllMilktype() {
    return milktypeService.GetAllMilktype();
  }

  @GetMapping("/lst/{id}")
  public MilkType getMilktype(@PathVariable("id") Long id) {
    return milktypeService.GetMilktypeById(id);
  }

  //http://localhost:1234/api/Milktype/add
  @PostMapping("/add")
  public ResponseEntity<?> addMilktype(@NonNull HttpServletRequest request,
    @RequestBody MilkType milktype) {
    Map<String, String> errors = MilkTypeValidator.validateMilkType(milktype);

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
    String checkDuplicateMessage = milktypeService.checkDuplicatMilkType(milktype.getMilkTypename());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "milkTypename",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    String token = jwtUtilities.getToken(request);
    milktypeService.AddMilktype(token, milktype);
    return ResponseEntity.ok(
      Map.of("status", "success", "message", "Thêm loại sữa thành công."));
  }//http://localhost:1234/api/Milktype/update/{id}

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateMilktype(@PathVariable("id") Long id,
    @RequestBody  MilkType milktype) {
    Map<String, String> errors = MilkTypeValidator.validateMilkType(milktype);

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
    String checkDuplicateMessage = milktypeService.checkDuplicatMilkType(milktype.getMilkTypename());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "milkTypename",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    return ResponseEntity.ok(
      Map.of("status", "success", "message", milktypeService.UpdateMilktype(id, milktype)));
  }//http://localhost:1234/api/Milktype/delete/{id}

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteMilktype(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    String message = milktypeService.DeleteMilktype(token, id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }

  @GetMapping("/getTypePage")
  public Page<MilkType> getTypePage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return milktypeService.GetMilktypePage(pageable);
  }

  @GetMapping("/getTypePageByName")
  public Page<MilkType> getTypePageByName(@RequestParam("milkTypeName") String milkTypeName,
    @RequestParam("page") int page, @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return milktypeService.GetMilktypePageByName(milkTypeName, pageable);
  }
}
