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
public class ProductController {
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
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        productService.deleteProduct(id, product);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Xóa thành công"));
    }

}
