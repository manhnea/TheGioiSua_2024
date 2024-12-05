package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.dto.ProductlstDto;
import com.example.TheGioiSua_2024.entity.Product;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

  List<Product> getAllProduct();

  String addProduct(String token, Product product);

  String updateProduct(String token, Long id, Product product);

  String deleteProduct(String token, Long id);

  Product getProductById(Long id);

  Page<ProductlstDto> getPageProduct(Pageable pageable);

  Page<ProductDto> getPageProductByTypeMilk(Pageable pageable, Long id);

  Page<ProductDto> getPageProductByBrandMilk(Pageable pageable, Long id);

  Page<ProductDto> getPageProductByTargetUser(Pageable pageable, Long id);

  Page<ProductDto> getPageProductWithSearch(String searchTerm, Pageable pageable);
}
