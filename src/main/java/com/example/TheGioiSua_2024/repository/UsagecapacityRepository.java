package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Usagecapacity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsagecapacityRepository extends JpaRepository<Usagecapacity, Long> {
    @Query("SELECT u FROM Usagecapacity u WHERE u.unit = ?1")
    Optional<Object> findByUnit(String unit);
}
