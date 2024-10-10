package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySizename(String sizeName);
}
