package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.MilkType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilktypeRepository extends JpaRepository<MilkType,Long> {
    Optional<MilkType> findByMilkTypename(String milkTypename);
    @Query("SELECT m FROM MilkType m WHERE m.milkTypename LIKE %:milkTypename%")
    Page<MilkType> findByMilkTypenameContaining(String milkTypename, Pageable pageable);
}
//a