package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin
@RestController
@RequestMapping("/Product")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    //http://localhost:1234/api/Product/lst
    @GetMapping("/lst")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    //http://localhost:1234/api/Product/add
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
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

        return ResponseEntity.ok(Map.of("status", "success", "message", productService.addProduct(product)));
    }

    @GetMapping("/lst/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    //http://localhost:1234/api/Product//update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody @Valid Product product, BindingResult bindingResult) {
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
        return ResponseEntity.ok(Map.of("status", "success", "message", productService.updateProduct(id, product)));
    }

    //http://localhost:1234/api/Product/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        String message = productService.deleteProduct(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", message));
    }

    //http://localhost:1234/api/Product/page
    @GetMapping("/page")
    public ResponseEntity<?> getPageProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(Map.of("status", "success", "message", productService.getPageProduct(pageable)));
    }
    //http://localhost:1234/api/Product/page/TypeMilk/{id}
    @GetMapping("/page/TypeMilk/{id}")
    public ResponseEntity<?> getPageProductByTypeMilk(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size, 
            @PathVariable Long id
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(Map.of("status", "success", "message", productService.getPageProductByTypeMilk(pageable,id)));
    }
    //http://localhost:1234/api/Product/page/BrandMilk/{id}
    @GetMapping("/page/BrandMilk/{id}")
    public ResponseEntity<?> getPageProductByBrandMilk(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size, 
            @PathVariable Long id
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(Map.of("status", "success", "message", productService.getPageProductByBrandMilk(pageable,id)));
    }
    //http://localhost:1234/api/Product/page/TargetUser/{id}
    @GetMapping("/page/TargetUser/{id}")
    public ResponseEntity<?> getPageProductByTargetUser(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size, 
            @PathVariable Long id
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(Map.of("status", "success", "message", productService.getPageProductByTargetUser(pageable,id)));
    }
}
