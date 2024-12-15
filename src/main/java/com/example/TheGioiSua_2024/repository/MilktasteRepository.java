package com.example.TheGioiSua_2024.repository;



import com.example.TheGioiSua_2024.entity.Milktaste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilktasteRepository extends JpaRepository<Milktaste, Long> {
    Optional<Milktaste> findByMilktastename(String milktasteName);
    @Query("SELECT m FROM Milktaste m WHERE m.milktastename LIKE %:milktasteName%")
    Page<Milktaste> findByMilktastenamePage(String milktasteName, Pageable pageable);
    boolean existsByMilktastename(String milktasteName);
}
