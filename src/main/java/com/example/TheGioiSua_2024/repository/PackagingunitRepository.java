package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackagingunitRepository extends JpaRepository<Packagingunit, Long> {
    Optional<Packagingunit> findByPackagingunitname(String packagingunitName);
    @Query("SELECT p FROM Packagingunit p WHERE p.packagingunitname LIKE %:packagingunitName%")
    Page<Packagingunit> findByPackagingunitPage(String packagingunitName, Pageable pageable);
    boolean existsByPackagingunitname(String packagingunitName);
    @Query(value = "SELECT p FROM Packagingunit p order by p.id desc")
    Page<Packagingunit> getPackagingunitPage(Pageable pageable);
}
