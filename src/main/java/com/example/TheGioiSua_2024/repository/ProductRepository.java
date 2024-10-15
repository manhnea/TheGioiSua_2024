package com.example.TheGioiSua_2024.repository;
import com.example.TheGioiSua_2024.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

import org.springframework.boot.autoconfigure.domain.EntityScan;
@EntityScan(basePackages = "com.example.TheGioiSua_2024.dto")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    @Query("SELECT p FROM Product p WHERE p.productname = :productname")
    Optional<Object> findByProductname(String productname);
}
