package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Usagecapacity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsagecapacityRepository extends JpaRepository<Usagecapacity, Long> {
    @Query("SELECT u FROM Usagecapacity u WHERE u.capacity = ?1 and u.unit = ?2")
    Optional<Usagecapacity> findByCapacityAndUnit(int capacity,String unit);
//    @Query("SELECT u FROM Usagecapacity u WHERE u.capacity =?1 and u.unit LIKE %:unit%")
//    Page<Usagecapacity> findByCapacityContaining(int capacity, String unit, Pageable pageable);
//    @Query("SELECT u FROM Usagecapacity u WHERE u.capacity = :capacity and u.unit = :unit")
    boolean existsByCapacityAndUnit(int capacity,String unit);
    @Query(value = "SELECT u FROM Usagecapacity u order by u.id desc")
    Page<Usagecapacity> getUsagecapacityPage(Pageable pageable);
}
