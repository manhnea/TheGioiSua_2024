package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Userinvoice;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserinvoiceRepository extends JpaRepository<Userinvoice, Long> {

  @Query("SELECT u.username, i.invoicecode, i.totalamount, i.status " +
    "FROM Userinvoice ui " +
    "JOIN ui.user u " +
    "JOIN ui.invoice i " +
    "WHERE ui.user.id != 1")
  List<Object[]> findUserInvoices();

  @Query("SELECT md.milkdetailcode, " +
    "SUM(od.quantity * od.price), " +
    "SUM(od.quantity), " +
    "COUNT(DISTINCT iv.id), " +
    "u.username " +
    "FROM Userinvoice ui " +
    "JOIN ui.invoice iv " +
    "JOIN ui.user u " +
    "JOIN Invoicedetail od ON iv.id = od.invoice.id " +
    "JOIN od.milkDetail md " +
    "WHERE (:voucherId IS NULL OR iv.voucher.id = :voucherId) " +
    "AND (iv.creationdate BETWEEN :startDate AND :endDate) " +
    "AND (:status IS NULL OR iv.status = :status) " +
    "AND u.username != 'admin' " +
    "GROUP BY md.milkdetailcode, u.username")
  List<Object[]> getSalesRevenue(
    @Param("voucherId") Long voucherId,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate,
    @Param("status") Integer status);
}
