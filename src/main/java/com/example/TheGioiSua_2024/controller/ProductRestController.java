package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.ProductService;
import com.example.TheGioiSua_2024.util.MilkbrandValidator;
import com.example.TheGioiSua_2024.util.ProductValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;

@CrossOrigin("*")
@RestController
@RequestMapping("/Product")
public class ProductRestController {

  @Autowired
  private ProductService productService;
  @Autowired
  private JwtUtilities jwtUtilities;

  //http://localhost:1234/api/Product/lst
  @GetMapping("/lst")
  public List<Product> getAllProduct() {
    return productService.getAllProduct();
  }

  //http://localhost:1234/api/Product/add
  @PostMapping("/add")
  public ResponseEntity<?> addProduct(@NonNull HttpServletRequest request,
      @RequestBody  Product product) {
    Map<String, String> errors = ProductValidator.validateProduct(product);
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
    String checkDuplicateMessage = productService.checkDuplicateproduct(product.getProductname());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "productname",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    String token = jwtUtilities.getToken(request);
    return ResponseEntity.ok(
        Map.of("status", "success", "message", productService.addProduct(token, product)));
  }

  @GetMapping("/lst/{id}")
  public Product getProduct(@PathVariable("id") Long id) {
    return productService.getProductById(id);
  }

  //http://localhost:1234/api/Product//update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateProduct(@NonNull HttpServletRequest request,
      @PathVariable("id") Long id,
      @RequestBody  Product product) {
    String token = jwtUtilities.getToken(request);
    Map<String, String> errors = ProductValidator.validateProduct(product);
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
    String checkDuplicateMessage = productService.checkDuplicateproduct(product.getProductname());
    if (checkDuplicateMessage != null) {
      List<Map<String, String>> errorList = new ArrayList<>();
      Map<String, String> error = Map.of(
              "field", "productname",
              "message", checkDuplicateMessage
      );
      errorList.add(error);
      return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errorList));
    }
    return ResponseEntity.ok(
        Map.of("status", "success", "message", productService.updateProduct(token, id, product)));
  }

  //http://localhost:1234/api/Product/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteProduct(@NonNull HttpServletRequest request,
      @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    String message = productService.deleteProduct(token, id);
    return ResponseEntity.ok(Map.of("status", "success", "message", message));
  }

  //http://localhost:1234/api/Product/page
  @GetMapping("/page")
  public ResponseEntity<?> getPageProduct(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(
        Map.of("status", "success", "message", productService.getPageProduct(pageable)));
  }

  //http://localhost:1234/api/Product/page/TypeMilk/{id}
  @GetMapping("/page/TypeMilk/{id}")
  public ResponseEntity<?> getPageProductByTypeMilk(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable Long id
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(Map.of("status", "success", "message",
        productService.getPageProductByTypeMilk(pageable, id)));
  }

  //http://localhost:1234/api/Product/page/BrandMilk/{id}
  @GetMapping("/page/BrandMilk/{id}")
  public ResponseEntity<?> getPageProductByBrandMilk(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable Long id
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(Map.of("status", "success", "message",
        productService.getPageProductByBrandMilk(pageable, id)));
  }

  //http://localhost:1234/api/Product/page/TargetUser/{id}
  @GetMapping("/page/TargetUser/{id}")
  public ResponseEntity<?> getPageProductByTargetUser(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable Long id
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(Map.of("status", "success", "message",
        productService.getPageProductByTargetUser(pageable, id)));
  }

  //http://localhost:1234/api/Product/page/getPageProductWithSearch/{searchTerm}
  @GetMapping("/page/getPageProductWithSearch/{searchTerm}")
  public ResponseEntity<?> getPageProductByTargetUser(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable String searchTerm
  ) {
    System.out.println("searchTerm" + searchTerm);
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(Map.of("status", "success", "message",
        productService.getPageProductWithSearch(searchTerm, pageable)));
  }
  @GetMapping("/productPage")
  public Page<Product> getProductPage(@RequestParam("page") int page, @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return productService.getProductPage(pageable);
  }
}
