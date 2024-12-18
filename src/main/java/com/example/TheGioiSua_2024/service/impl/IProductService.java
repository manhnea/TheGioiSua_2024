package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.dto.ProductDtos;
import com.example.TheGioiSua_2024.dto.ProductlstDto;
import com.example.TheGioiSua_2024.entity.Product;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IProductService {

  List<Product> getAllProduct();

  ResponseEntity<?> addProduct(String token, Product product);

  ResponseEntity<?> updateProduct(String token, Long id, Product product);

  ResponseEntity<?> deleteProduct(String token, Long id);

  Product getProductById(Long id);

  Page<ProductlstDto> getPageProduct(Pageable pageable);

  Page<ProductDto> getPageProductByTypeMilk(Pageable pageable, Long id);

  Page<ProductDto> getPageProductByBrandMilk(Pageable pageable, Long id);

  Page<ProductDto> getPageProductByTargetUser(Pageable pageable, Long id);

  Page<ProductDto> getPageProductWithSearch(String searchTerm, Pageable pageable);
  Page<Product> getProductPage(Pageable pageable);

  Page<Product> getProductPageByTypeMilk(String productname, Pageable pageable);
  Page<Product> filterProduct(String productname,Long milkBrandId,Long targetUserId, Long milkTypeId,  Pageable pageable);

  Page<Product> getNewProduct(Pageable pageable);

  Page<ProductDtos> getBestSeller(Pageable pageable);
}
