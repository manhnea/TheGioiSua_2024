package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackagingunitRepository extends JpaRepository<Packagingunit, Long> {
    Optional<Packagingunit> findByPackagingunitname(String packagingunitName);
}
