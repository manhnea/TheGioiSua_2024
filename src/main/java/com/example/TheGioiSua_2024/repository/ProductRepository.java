package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@EntityScan(basePackages = "com.example.TheGioiSua_2024.dto")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    @Query("SELECT p FROM Product p WHERE p.productname = :productname")
    Optional<Object> findByProductname(String productname);

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductDto( " +
       "p.id, " +
       "mt.id, " +
       "mb.id, " +
       "tt.id, " +
       "mt.milkTypename, " +
       "mb.milkbrandname, " +
       "tt.targetName, " +
       "p.status) " +
       "FROM Product p " +
       "JOIN p.milkBrand mb " + 
       "JOIN p.milkType mt " +  
       "JOIN p.targetUser tt " + 
       "WHERE p.status = 1")
Page<ProductDto> getPageProduct(Pageable pageable);


}
