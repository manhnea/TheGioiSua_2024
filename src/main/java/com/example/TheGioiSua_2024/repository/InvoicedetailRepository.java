package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.InvoiceDetailDto;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicedetailRepository extends JpaRepository<Invoicedetail, Long> {

    @Query("SELECT new com.example.TheGioiSua_2024.dto.InvoiceDetailDto("
            + "id.id, pu.packagingunitname, mt.milkTypename, mb.milkbrandname, "
            + "mtt.milktastename, uc.capacity, uc.unit, id.quantity, id.price, id.totalprice, id.status) "
            + "FROM Invoicedetail id "
            + "JOIN id.milkDetail md "
            + "JOIN md.product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "JOIN md.usageCapacity uc "
            + "JOIN md.packagingunit pu "
            + "JOIN md.milkTaste mtt "
            + "WHERE id.invoice.id = :invoiceId")
    List<InvoiceDetailDto> findInvoiceDetailsByInvoiceId( Long invoiceId);

}
