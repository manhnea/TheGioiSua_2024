package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilkbrandRepository extends JpaRepository<Milkbrand, Long> {
    Optional<Milkbrand> findByMilkbrandname(String milkbrandname);
}
