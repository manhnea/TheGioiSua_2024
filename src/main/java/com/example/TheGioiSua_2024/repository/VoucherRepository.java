package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Voucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query("select a from Voucher a WHERE a.vouchercode = ?1")
    Optional<Voucher> findByVoucher(String voucherName);

    Voucher vouchercode(String vouchercode);

    @Query(value = "SELECT COUNT(ui) > 0\n"
            + "    FROM Userinvoice ui\n"
            + "    JOIN ui.invoice i\n"
            + "    JOIN ui.user u\n"
            + "    JOIN i.voucher v\n"
            + "    WHERE u.id = :userId AND v.vouchercode = :voucherCode")
    boolean existsUserInvoiceByUserAndVoucher(Long userId, String voucherCode);

    @Query(value = "SELECT * \n"
            + "FROM voucher\n"
            + "WHERE usagecount > 0\n"
            + "  AND startdate < CURDATE()\n"
            + "  AND CURDATE() < enddate\n"
            + "  AND `status` = 1", nativeQuery = true)
    List<Voucher> voucherActive();

}
