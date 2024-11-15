package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Userinvoice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserinvoiceRepository extends JpaRepository<Userinvoice, Long> {

  @Query("SELECT u.username, i.invoicecode, i.totalamount, i.status " +
      "FROM Userinvoice ui " +
      "JOIN ui.user u " +
      "JOIN ui.invoice i " +
      "WHERE ui.user.id != 1")
  List<Object[]> findUserInvoices();
}
