package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.Product;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    List<Product> getAllProduct();
    String addProduct(Product product);

    String updateProduct(Long id, Product product);

    void deleteProduct(Long id, Product product);
    
    Page<ProductDto> getPage(Pageable pageable);
}
