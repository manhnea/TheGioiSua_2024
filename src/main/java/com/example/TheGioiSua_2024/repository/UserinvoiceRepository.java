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
    "iv.totalamount," +
    "SUM(od.quantity), " +
    "v.vouchercode," +
    "u.username " +
    "FROM Userinvoice ui " +
    "JOIN ui.invoice iv " +
    "JOIN ui.user u " +
    "JOIN Invoicedetail od ON iv.id = od.invoice.id " +
    "JOIN Voucher v ON iv.voucher.id = v.id " +
    "JOIN od.milkDetail md " +
    "WHERE (:voucher IS NULL OR v.vouchercode = :voucher) " +
    "AND (:startDate IS NULL OR :endDate IS NULL OR iv.creationdate BETWEEN :startDate AND :endDate) "
    +
    "AND u.username != 'admin' " +
    "AND iv.status = 913 " +
    "GROUP BY md.milkdetailcode, u.username")
  List<Object[]> getSalesRevenue(
    @Param("voucher") String voucher,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate);


  @Query(value = "SELECT DATE(i.creationdate) AS ngayHoaDon, SUM(i.totalamount) AS tongDoanhThu " +
    "FROM Invoice i " +
    "JOIN Userinvoice ui ON ui.invoice.id = i.id " +
    "WHERE i.status = 913 " +
    "AND (:startDate IS NULL OR :endDate IS NULL OR i.creationdate BETWEEN :startDate AND :endDate)"
    +
    "AND (:voucherid IS NULL OR i.voucher.id = :voucherid)" +
    "GROUP BY DATE(i.creationdate) " +
    "ORDER BY DATE(i.creationdate)")
  List<Object[]> findRevenueByDate(@Param("voucherid") Integer voucherid,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate);
}
