package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/get")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if (fieldError != null) {
                return fieldError.getDefaultMessage();
            }
        }
        String resultMessage = productService.addProduct(product);

        return resultMessage;
    }
    @PutMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @RequestBody @Valid Product product, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if (fieldError != null) {
                return fieldError.getDefaultMessage();
            }
        }
        return productService.updateProduct(id, product);
    }
    @PutMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        productService.deleteProduct(id, product);
        return "Xóa sản phẩm thành công.";
    }
}
