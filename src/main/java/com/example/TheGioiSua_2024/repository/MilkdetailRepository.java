package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilkdetailRepository extends JpaRepository<Milkdetail, Long> {
    Optional<Milkdetail> existsBymilkdetailcode(String milkdetailcode);

}
