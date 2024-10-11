package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.MilkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilktypeRepository extends JpaRepository<MilkType,Long> {
    Optional<MilkType> findByMilkTypename(String milkTypename);
}
//a