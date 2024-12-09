package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.InvoiceDetailDto;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicedetailRepository extends JpaRepository<Invoicedetail, Long> {

  // Truy vấn đầu tiên trả về InvoiceDetailDto
  @Query("SELECT new com.example.TheGioiSua_2024.dto.InvoiceDetailDto(" +
      "id.id, pu.packagingunitname, mt.milkTypename, mb.milkbrandname, " +
      "mtt.milktastename, uc.capacity, uc.unit, id.quantity, id.price, id.totalprice, id.status) " +
      "FROM Invoicedetail id " +
      "JOIN id.milkDetail md " +
      "JOIN md.product p " +
      "JOIN p.milkBrand mb " +
      "JOIN p.milkType mt " +
      "JOIN md.usageCapacity uc " +
      "JOIN md.packagingunit pu " +
      "JOIN md.milkTaste mtt " +
      "WHERE id.invoice.id = :invoiceId")
  List<InvoiceDetailDto> findInvoiceDetailsByInvoiceId(@Param("invoiceId") Long invoiceId);

  // Truy vấn native SQL để tính doanh số hàng tháng
  @Query(value = "SELECT " +
      "    YEAR(invoice.creationdate) AS year, " +
      "    MONTH(invoice.creationdate) AS month, " +
      "    SUM(invoicedetail.totalprice) AS total_sales_value, " +
      "    LAG(SUM(invoicedetail.totalprice)) OVER (ORDER BY YEAR(invoice.creationdate), MONTH(invoice.creationdate)) AS previous_month_sales, "
      +
      "    CASE " +
      "        WHEN LAG(SUM(invoicedetail.totalprice)) OVER (ORDER BY YEAR(invoice.creationdate), MONTH(invoice.creationdate)) IS NULL THEN NULL "
      +
      "        ELSE (SUM(invoicedetail.totalprice) - LAG(SUM(invoicedetail.totalprice)) OVER (ORDER BY YEAR(invoice.creationdate), MONTH(invoice.creationdate))) / LAG(SUM(invoicedetail.totalprice)) OVER (ORDER BY YEAR(invoice.creationdate), MONTH(invoice.creationdate)) * 100 "
      +
      "    END AS growth_percentage " +
      "FROM " +
      "    invoicedetail " +
      "    JOIN invoice ON invoicedetail.invoiceid = invoice.id " +
      "WHERE " +
      "    invoice.creationdate >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
      "    AND invoice.status = 905 " +
      "GROUP BY " +
      "    YEAR(invoice.creationdate), " +
      "    MONTH(invoice.creationdate) " +
      "ORDER BY " +
      "    year DESC, " +
      "    month DESC", nativeQuery = true)
  List<Object[]> findMonthlySalesGrowthNative();


  @Query("SELECT\n"
      + "    i.id ,\n" // Add invoice ID to the result
      + "    i.invoicecode,\n"
      + "    i.deliveryaddress,\n"
      + "    i.phonenumber,\n"
      + "    md.description AS milkdetail_description,\n"
          + "    id.totalprice,\n"
          + "id.price,\n"
      + "    id.quantity,\n"
      + "    mt.milktastename,\n"
      + "    mtype.milkType.milkTypename,\n"
      + "    uc.capacity,\n"
          + "    uc.unit,\n"
          + "i.status,\n"
          +"id.id,\n"
          +"i.fullname,\n"
          +"id.milkDetail.id \n"
      + "FROM\n"
      + "    Invoicedetail id\n"
      + "        JOIN\n"
      + "    id.invoice i\n"
      + "        JOIN\n"
      + "    id.milkDetail md\n"
      + "        JOIN\n"
      + "    md.milkTaste mt\n"
      + "        JOIN\n"
      + "    md.product mtype\n"
      + "        JOIN\n"
      + "    md.usageCapacity uc\n"
      + "WHERE\n"
      + "    i.id = :invoiceId")
  List<Object[]> findInvoiceAdminDetails(@Param("invoiceId") Long invoiceId);


  @Query(value = "SELECT " +
      "m.id, " +
      "id.price, " +
      "p.productname, " +
      "mt.milktastename, " +
      "pu.packagingunitname, " +
      "uc.capacity, " +
      "uc.unit, " +
      "SUM(id.totalprice), " +
      "SUM(id.quantity) " +
      "FROM invoicedetail id " +
      "JOIN invoice i ON id.invoiceid = i.id " +
      "JOIN milkdetail m ON id.milkdetailid = m.id " +
      "JOIN product p ON m.productid = p.id " +
      "JOIN milktaste mt ON m.milktasteid = mt.id " +
      "JOIN packagingunit pu ON m.packagingunitid = pu.id " +
      "JOIN usagecapacity uc ON m.usagecapacityid = uc.id " +
      "WHERE i.status = 905 " +
      "GROUP BY m.id, p.productname, mt.milktastename, pu.packagingunitname, uc.capacity, uc.unit "
      +
      "ORDER BY SUM(id.totalprice) DESC", nativeQuery = true)
  List<Object[]> getMilkSalesDetails();


  //  Tinh tong so luong, so tien cua tat ca hoa don
  @Query(
      "SELECT invoice.id AS invoiceId, invoice.invoicecode AS invoiceCode, SUM(invoicedetail.quantity) AS totalQuantity, invoice.totalamount AS totalAmount "
          +
          "FROM Invoicedetail invoicedetail " +
          "JOIN invoicedetail.invoice invoice " +
          "WHERE invoice.status = 905 " +
          "GROUP BY invoice.id, invoice.invoicecode, invoice.totalamount")
  List<Object[]> findInvoiceSummaries();

  @Query("SELECT SUM(invoice.totalamount) FROM Invoice invoice WHERE invoice.status = 338")
  Double findTotalAmount();
  @Query("SELECT id FROM Invoicedetail id where id.invoice.id = :invoiceId")
  List<Invoicedetail> invoicedetails(Long invoiceId);
}
