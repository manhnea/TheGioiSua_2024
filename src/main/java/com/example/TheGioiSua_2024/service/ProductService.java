package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.repository.ProductRepository;
import com.example.TheGioiSua_2024.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MilktypeRepository milktypeRepository;
    @Autowired
    private MilktypeRepository milkTypeRepository;
    @Autowired
    private TargetuserService targetuserService;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public String addProduct(Product product) {
        String productCode = product.getProductCode().trim();
        product.setProductCode(productCode);
        if (productRepository.findByProductCode(productCode).isPresent()) {
            return "Sản phẩm với mã này đã tồn tại.";
        }
        product.setStatus(1);
        productRepository.save(product);
        return "Thêm sản phẩm thành công.";
    }

    @Override
    public String updateProduct(Long id, Product product) {
        String productCode = product.getProductCode().trim();
        product.setProductCode(productCode);

        if (productRepository.findByProductCode(productCode).isPresent()) {
            return "Sản phẩm với mã này đã tồn tại.";
        }


        Product existingProduct = productRepository.findById(id).orElseThrow();
        MilkType milkType = milktypeRepository.findById(product.getMilktype().getId()).orElseThrow();
//        Milkbrand milkbrand = milkTypeRepository.findById(product.getMilkbrand().getId()).orElseThrow();
        existingProduct.setMilktype(milkType);
        existingProduct.setProductCode(product.getProductCode());
        existingProduct.setStatus(1);
        productRepository.save(existingProduct);
        return "Cập nhật sản phẩm thành công.";
    }

    @Override
    public void deleteProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow();
        existingProduct.setStatus(0);
        productRepository.save(existingProduct);
    }
}
