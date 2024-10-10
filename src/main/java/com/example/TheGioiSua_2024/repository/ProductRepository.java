package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
