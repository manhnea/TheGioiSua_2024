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

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public String addProduct(Product product) {
        String productCode = product.getProductCode().trim();
        product.setProductCode(productCode);

        String productName = product.getProductName().trim();
        product.setProductName(productName);

        // Check for existing product code
        if (productRepository.findByProductCode(productCode).isPresent()) {
            return "Sản phẩm với mã này đã tồn tại.";
        }

        // Check for existing product name
        if (productRepository.findByProductName(productName).isPresent()) {
            return "Sản phẩm với tên này đã tồn tại.";
        }

        product.setStatus(1); // Assuming '1' indicates active or available status
        productRepository.save(product);
        return "Thêm sản phẩm thành công.";
    }
}
