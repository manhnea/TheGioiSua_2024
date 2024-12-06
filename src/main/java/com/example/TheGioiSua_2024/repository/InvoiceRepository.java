package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  Optional<Invoice> existsByInvoicecode(String milkbrandname);

  @Query("SELECT new com.example.TheGioiSua_2024.dto.InvoiceDto("
    + "i.id, i.invoicecode, buyer.fullname, seller.fullname, "
    + "i.creationdate, i.deliveryaddress, i.phonenumber, i.paymentmethod, v.vouchercode, "
    + "i.discountamount, i.totalamount, i.status) "
    + "FROM Invoice i "
    + "JOIN i.userInvoices uvBuyer "
    + "LEFT JOIN i.voucher v "
    + "JOIN uvBuyer.user buyer "
    + "JOIN buyer.role rBuyer "
    + "LEFT JOIN i.userInvoices uvSeller "
    + "LEFT JOIN uvSeller.user seller "
    + "LEFT JOIN seller.role rSeller "
    + "WHERE rBuyer.id = 2 "
    + "AND (rSeller.id = 1 OR seller IS NULL) "
    + "AND uvBuyer <> uvSeller "
    + "AND uvSeller.status = uvBuyer.status "
    + "AND buyer.id = :buyerId")
  List<InvoiceDto> findInvoices(Long buyerId);

  @Query("SELECT COALESCE(MAX(i.id), 0) FROM Invoice i")
  Integer findMaxId();

  @Query("SELECT a FROM Invoice a where a.invoicecode = ?1")
  Invoice findbycode(String description);

  @Query(value = "SELECT * FROM invoice WHERE status = 334", nativeQuery = true)
  List<String> findInvoicesByStatus334();

  @Query("SELECT COUNT(*) "
    + "FROM Invoice "
    + "WHERE status = 334 "
    + "AND MONTH(creationdate) = :month "
    + "AND YEAR(creationdate) = :year")
  long countInvoices(@Param("month") int month, @Param("year") int year);

  @Query(
    "SELECT iv.id, iv.invoicecode, iv.creationdate, iv.voucher.id, iv.totalamount, iv.deliveryaddress, iv.discountamount, iv.paymentmethod, iv.status, iv.phonenumber "
      +
      "FROM Invoice iv " +
      "JOIN Userinvoice ui ON ui.invoice.id = iv.id " +
      "JOIN User u ON ui.user.id = u.id " +
      "WHERE (:status IS NULL OR iv.status = :status) " +
      "AND (:invoiceCode IS NULL OR iv.invoicecode = :invoiceCode) " +
      "AND (:username IS NULL OR u.username = :username) " +
      "AND (:startDate IS NULL OR :endDate IS NULL OR iv.creationdate BETWEEN :startDate AND :endDate) "
      +
      "ORDER BY iv.id DESC"
  )
  Page<Invoice> findInvoices(@Param("status") String status,
    @Param("invoiceCode") String invoiceCode,
    @Param("username") String username,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate, Pageable pageable);

  @Query("SELECT DATE(i.creationdate), SUM(i.totalamount) " +
    "FROM Invoice i " +
    "WHERE MONTH(i.creationdate) = MONTH(CURRENT_DATE) " +
    "AND YEAR(i.creationdate) = YEAR(CURRENT_DATE) " +
    "AND i.status = 905 " +
    "GROUP BY DATE(i.creationdate) " +
    "ORDER BY DATE(i.creationdate) ASC")
  List<Object[]> findRevenueByDate();


}
