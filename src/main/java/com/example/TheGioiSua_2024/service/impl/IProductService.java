package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.Product;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    List<Product> getAllProduct();
    String addProduct(Product product);

    String updateProduct(Long id, Product product);

    String deleteProduct(Long id);

    Product getProductById(Long id);
}
