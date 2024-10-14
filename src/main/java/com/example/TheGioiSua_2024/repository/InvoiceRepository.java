package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT a FROM Invoice a WHERE a.invoicecode = ?1")
    boolean existsByInvoicecode(String invoiceCode);
}
