package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.InvoiceDetailAdminDTO;
import com.example.TheGioiSua_2024.dto.InvoiceDetailDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.entity.Voucher;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.InvoicedetailRepository;
import com.example.TheGioiSua_2024.repository.MilkdetailRepository;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.service.impl.IInvoicedetailService;
import com.example.TheGioiSua_2024.util.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class InvoicedetailService implements IInvoicedetailService {

  @Autowired
  private InvoicedetailRepository invoicedetailRepository;

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private MilkdetailRepository milkdetailRepository;
    @Autowired
    private VoucherRepository voucherRepository;

  @Override
  public List<Invoicedetail> getInvoicedetailList() {
    return invoicedetailRepository.findAll();
  }

  @Override
  public ResponseEntity<?> saveInvoicedetail(Invoicedetail invoicedetail) {
    try {
      // Tìm Milkdetail theo ID
      Milkdetail milkdetail = milkdetailRepository
          .findById(invoicedetail.getMilkDetail().getId())
          .orElseThrow(() -> new RuntimeException("Không Tồn Tại"));
      // Kiểm tra số lượng tồn kho
      if (invoicedetail.getQuantity() > milkdetail.getStockquantity()) {
        Invoice invoice = invoiceRepository.findById(invoicedetail.getInvoice().getId())
            .orElseThrow(() -> new RuntimeException("Hoá Đơn Không Tồn Tại"));
        invoiceRepository.delete(invoice);
        return ResponseEntity.badRequest().body(Map.of("error",
            "Số Lượng Không Phù Hợp\n Số Lượng Còn Lại:" + milkdetail.getStockquantity()));
      }
//      // Cập nhật số lượng tồn kho
//      milkdetail.setStockquantity(milkdetail.getStockquantity() - invoicedetail.getQuantity());
//      // Lưu lại Milkdetail
//      milkdetailRepository.save(milkdetail);
//      // Đặt trạng thái cho Invoicedetail
//      invoicedetail.setStatus(Status.Active);
//      // Lưu lại Invoicedetail
      invoicedetailRepository.save(invoicedetail);
      // Trả về phản hồi thành công
      return ResponseEntity.ok(Map.of("message", "Thêm Thành Công"));
    } catch (RuntimeException e) {
      // Trả về phản hồi lỗi
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @Override
  public String updateInvoicedetail(Long id, Invoicedetail invoicedetail) {
    Invoicedetail existingInvoicedetail = invoicedetailRepository.findById(id).orElseThrow();
    Invoice invoice = invoiceRepository.findById(existingInvoicedetail.getInvoice().getId())
        .orElseThrow();
    Milkdetail milkdetail = milkdetailRepository.findById(
        existingInvoicedetail.getMilkDetail().getId()).orElseThrow();
    existingInvoicedetail.setInvoice(invoice);
    existingInvoicedetail.setMilkDetail(milkdetail);
    existingInvoicedetail.setQuantity(invoicedetail.getQuantity());
    existingInvoicedetail.setPrice(invoicedetail.getPrice());
    existingInvoicedetail.setTotalprice(invoicedetail.getTotalprice());
    existingInvoicedetail.setStatus(Status.Active);
    return "Cập nhật chi tiết hóa đơn thành công!";
  }

  @Override
  public String deleteInvoicedetail(Long id) {
    Invoicedetail existingInvoicedetail = invoicedetailRepository.findById(id).orElseThrow();
    if (existingInvoicedetail.getStatus() == Status.Delete) {
      existingInvoicedetail.setStatus(Status.Active);
      invoicedetailRepository.save(existingInvoicedetail);
      return "Chi tiết hóa đơn đã bị xóa!";
    } else {
      existingInvoicedetail.setStatus(Status.Delete);
      invoicedetailRepository.save(existingInvoicedetail);
      return "Xóa chi tiết hóa đơn thành công!";
    }
  }

  public Invoicedetail getInvoicedetailById(Long id) {
    return invoicedetailRepository.findById(id).orElseThrow();
  }

  @Override
  public List<InvoiceDetailDto> findInvoiceDetailsByInvoiceId(Long invoiceId) {
    return invoicedetailRepository.findInvoiceDetailsByInvoiceId(invoiceId);
  }

  @Override
  public List<Map<String, Object>> getMonthlySalesGrowth() {
    List<Object[]> result = invoicedetailRepository.findMonthlySalesGrowthNative();
    List<Map<String, Object>> data = new ArrayList<>();

    for (Object[] row : result) {
      Map<String, Object> map = new HashMap<>();
      map.put("year", row[0]);
      map.put("month", row[1]);
      map.put("total_sales_value", row[2]);
      map.put("previous_month_sales", row[3]);
      map.put("growth_percentage", row[4]);
      data.add(map);
    }

    return data;
  }

  @Override
  public List<Map<String, Object>> findInvoiceAdminDetails(Long invoiceId) {
    List<Object[]> results = invoicedetailRepository.findInvoiceAdminDetails(invoiceId);

    // Group by invoice details (invoiceCode, deliveryAddress, phoneNumber)
    Map<String, Map<String, Object>> groupedInvoices = new HashMap<>();

    for (Object[] record : results) {
      // Extract the fields from the record
      Long id = (Long) record[0];  // invoiceId
      String invoiceCode = (String) record[1];
      String deliveryAddress = (String) record[2];
      String phoneNumber = (String) record[3];
      int status = (int) record[12];
String fullname = (String) record[14];
      String groupKey = invoiceCode + "-" + deliveryAddress + "-" + phoneNumber;

      // If the invoice group doesn't exist, create it
      groupedInvoices.putIfAbsent(groupKey, new HashMap<>() {{
        put("id", id);  // Add the invoiceId
        put("invoiceCode", invoiceCode);
        put("deliveryAddress", deliveryAddress);
        put("phoneNumber", phoneNumber);
        put("status", status);
        put("fullname", fullname);
        put("items", new ArrayList<Map<String, Object>>());
      }});

      // Add line item details to the items list within the grouped invoice
      List<Map<String, Object>> items = (List<Map<String, Object>>) groupedInvoices.get(groupKey)
          .get("items");
      Map<String, Object> itemDetails = new HashMap<>();
      itemDetails.put("milkDetailDescription", record[4]);
      itemDetails.put("totalAmount", record[5]);
      itemDetails.put("price", record[6]);
      itemDetails.put("quantity", record[7]);
      itemDetails.put("milkTasteName", record[8]);
      itemDetails.put("milkTypeName", record[9]);
      itemDetails.put("capacity", record[10]);
      itemDetails.put("unit", record[11]);
        itemDetails.put("ida", record[13]);
      items.add(itemDetails);
    }

    // Convert the grouped invoices map to a list
    return new ArrayList<>(groupedInvoices.values());
  }

  @Override
  public List<Map<String, Object>> getMilkSalesDetails() {
    List<Object[]> rawResults = invoicedetailRepository.getMilkSalesDetails();

    // Create a list to hold the map of result data
    List<Map<String, Object>> response = new ArrayList<>();

    for (Object[] row : rawResults) {
      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("id", row[0]);
      resultMap.put("productName", row[2]);
      resultMap.put("milkTasteName", row[3]);
      resultMap.put("packagingUnitName", row[4]);
      resultMap.put("capacity", row[5]);
      resultMap.put("capacityUnit", row[6]);
      resultMap.put("totalSalesValue", row[7]);
      resultMap.put("totalQuantity", row[8]);

      response.add(resultMap);
    }
    return response;
  }

  @Override
  public Map<String, Object> getInvoiceSummary() {
    List<Object[]> invoiceSummaries = invoicedetailRepository.findInvoiceSummaries();
    Double totalAmount = invoicedetailRepository.findTotalAmount();

    List<Map<String, Object>> invoices = new ArrayList<>();
    for (Object[] row : invoiceSummaries) {
      Map<String, Object> invoice = new HashMap<>();
      invoice.put("invoiceId", row[0]);
      invoice.put("invoiceCode", row[1]);
      invoice.put("totalQuantity", row[2]);
      invoice.put("totalAmount", row[3]);
      invoices.add(invoice);
    }

    Map<String, Object> result = new HashMap<>();
    result.put("invoices", invoices);
    result.put("totalAmount", totalAmount);

    return result;
  }

  @Override
  public ResponseEntity<String> updateCountinvoicedetail(Long id, Invoicedetail invoicedetail) {
    Invoicedetail existingInvoicedetail = invoicedetailRepository.findById(id).orElse(null);
    if (existingInvoicedetail == null) {
      return ResponseEntity.status(404).body("Không tìm thấy chi tiết hóa đơn với id: " + id);
    }
    Invoice invoice = invoiceRepository.findById(existingInvoicedetail.getInvoice().getId()).orElse(null);

    if (invoice == null) {
      return ResponseEntity.status(404).body("Không tìm thấy hóa đơn với id: " + existingInvoicedetail.getInvoice().getId());
    }
    if (invoice.getStatus() == Status.AwaitingPayment) {

      existingInvoicedetail.setQuantity(invoicedetail.getQuantity());
      existingInvoicedetail.setTotalprice(invoicedetail.getQuantity() * existingInvoicedetail.getPrice());
      invoicedetailRepository.save(existingInvoicedetail);
      return ResponseEntity.status(200).body("Cập nhật số lượng chi tiết hóa đơn thành công!");
    } else {
      return ResponseEntity.status(400).body("Không thể cập nhật chi tiết hóa đơn vì trạng thái hóa đơn không phải Pending!");
    }
  }

}
