package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> existsByInvoicecode(String milkbrandname);

    @Query("SELECT new com.example.TheGioiSua_2024.dto.InvoiceDto("
            + "i.id, i.invoicecode, buyer.username, seller.username, "
            + "i.creationdate, i.deliveryaddress,i.phonenumber,i.paymentmethod,v.vouchercode, i.discountamount, i.totalamount, i.status) "
            + "FROM Invoice i "
            + "JOIN i.userInvoices uvBuyer "
            + "LEFT JOIN i.voucher v "
            + "JOIN uvBuyer.user buyer "
            + "JOIN buyer.role rBuyer "
            + "JOIN i.userInvoices uvSeller "
            + "JOIN uvSeller.user seller "
            + "JOIN seller.role rSeller "
            + "WHERE rBuyer.id = 2 "
            + "AND rSeller.id = 1 "
            + "AND uvBuyer <> uvSeller "
            + "AND uvSeller.status = uvBuyer.status "
            + "AND buyer.id = :buyerId")

    List<InvoiceDto> findInvoices(Long buyerId);

    @Query("SELECT COALESCE(MAX(i.id), 0) FROM Invoice i")
    Integer findMaxId();
}
