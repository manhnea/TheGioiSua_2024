package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Userinvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserinvoiceRepository extends JpaRepository<Userinvoice, Long> {
}
