package com.example.TheGioiSua_2024.repository;


import com.example.TheGioiSua_2024.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("select a from Voucher a WHERE a.vouchercode = ?1")
    Optional<Voucher> findByVoucher(String voucherName);
}
