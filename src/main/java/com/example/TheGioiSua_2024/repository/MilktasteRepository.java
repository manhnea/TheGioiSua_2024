package com.example.TheGioiSua_2024.repository;



import com.example.TheGioiSua_2024.entity.Milktaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilktasteRepository extends JpaRepository<Milktaste, Long> {
    Optional<Milktaste> findByMilktastename(String milktasteName);
}
