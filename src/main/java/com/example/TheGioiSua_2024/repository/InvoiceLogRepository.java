/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.InvoiceLogDto;
import com.example.TheGioiSua_2024.entity.InvoiceLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hieu
 */
@Repository
public interface InvoiceLogRepository extends JpaRepository<InvoiceLog, Long> {

    @Query("SELECT new com.example.TheGioiSua_2024.dto.InvoiceLogDto(il.created_at,il.description, il.status) FROM InvoiceLog il JOIN il.invoice i WHERE i.id = :invoiceId")
    List<InvoiceLogDto> getInvoiceLogByInvoiceId(Long invoiceId);

}
