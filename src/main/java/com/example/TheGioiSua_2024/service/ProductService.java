package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.repository.MilkbrandRepository;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.repository.ProductRepository;
import com.example.TheGioiSua_2024.repository.TargetuserRepository;
import com.example.TheGioiSua_2024.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MilktypeRepository milktypeRepository;
    @Autowired
    private MilkbrandRepository milkbrandRepository;
    @Autowired
    private TargetuserRepository targetuserRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public String addProduct(Product product) {

        String productname = product.getProductname().trim();
        String productCode = product.getProductCode().trim();
        product.setProductCode(productCode);
        product.setProductname(productname);
        if (productRepository.findByProductname(productname).isPresent()) {
            return "Sản phẩm với tên này đã tồn tại.";
        }
        if (productRepository.findByProductCode(productCode).isPresent()) {
            return "Sản phẩm với mã này đã tồn tại.";
        }
        product.setStatus(1);
        productRepository.save(product);
        return "Thêm sản phẩm thành công.";
    }

    @Override
    public String updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow();
        MilkType milkType = milktypeRepository.findById(existingProduct.getMilkType().getId()).orElseThrow();
        Milkbrand milkbrand = milkbrandRepository.findById(existingProduct.getMilkBrand().getId()).orElseThrow();
        Targetuser targetuser = targetuserRepository.findById(existingProduct.getTargetUser().getId()).orElseThrow();
        String currentProductName = existingProduct.getProductname();
        String currentProductCode = existingProduct.getProductCode();
        if (currentProductName.equals(product.getProductname()) && currentProductCode.equals(product.getProductCode())) {
            existingProduct.setMilkType(milkType);
            existingProduct.setMilkBrand(milkbrand);
            existingProduct.setTargetUser(targetuser);
            existingProduct.setProductCode(product.getProductCode());
            existingProduct.setStatus(1);
            productRepository.save(existingProduct);
            return "Cập nhật sản phẩm thành công.";
        } else if (productRepository.findByProductname(product.getProductname()).isPresent()) {
            return "Sản phẩm với tên này đã tồn tại.";
        } else if (productRepository.findByProductCode(product.getProductCode()).isPresent()) {
            return "Sản phẩm với mã này đã tồn tại.";
        }
        existingProduct.setMilkType(milkType);
        existingProduct.setMilkBrand(milkbrand);
        existingProduct.setTargetUser(targetuser);
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

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }
}
