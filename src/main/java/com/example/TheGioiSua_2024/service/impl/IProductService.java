package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProduct();
    String addProduct(Product product);

    String updateProduct(Long id, Product product);

    void deleteProduct(Long id, Product product);
}
