package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
}
