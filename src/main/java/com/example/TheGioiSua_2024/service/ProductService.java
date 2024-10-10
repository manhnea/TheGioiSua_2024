package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.repository.ProductRepository;
import com.example.TheGioiSua_2024.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
@Autowired
private ProductRepository productRepository;
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
