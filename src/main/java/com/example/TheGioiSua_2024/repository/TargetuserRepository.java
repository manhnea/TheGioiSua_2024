package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Size;
import com.example.TheGioiSua_2024.entity.Targetuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TargetuserRepository extends JpaRepository<Targetuser, Long> {
    @Query("SELECT t FROM Targetuser t WHERE t.targetName = ?1")
    Optional<Targetuser> findByTargetusername(String targetName);
}
