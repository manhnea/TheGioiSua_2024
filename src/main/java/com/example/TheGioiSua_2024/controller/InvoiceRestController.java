package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.dto.InvoiceDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.service.InvoiceService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Invoice")
public class InvoiceRestController {

    @Autowired
    private InvoiceService invoiceService;

    //RessourceEndPoint:http://localhost:1234/api/Invoice/lst
    @GetMapping("/lst")
    public List<Invoice> listInvoice() {
        return invoiceService.getInvoiceList();
    }

    @GetMapping("/lst/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/add
    @PostMapping("/add")
    public ResponseEntity<?> addInvoice(@RequestBody InvoiceDto invoiceDto) {
        return invoiceService.saveInvoice(invoiceDto);
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/update
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDto invoiceDto) {
        invoiceService.updateInvoice(id, invoiceDto);
        return invoiceService.updateInvoice(id, invoiceDto);
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id) {
        String message = invoiceService.deleteInvoice(id);
        return ResponseEntity.ok(Map.of("status", "success", "message", message));
    }

    //RessourceEndPoint:http://localhost:1234/api/Invoice/getInvoices/{userid}
//
//
    @GetMapping("/getInvoices/{userid}")
    public ResponseEntity<?> getInvoices(@PathVariable Long userid) {
        List<InvoiceDto> invoiceDtos = invoiceService.getInvoices(userid);
        if (invoiceDtos.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("status", "error"));
        }
        return ResponseEntity.ok(Map.of("message", invoiceDtos));
    }
    @GetMapping("/getInvoicespage/{buyerId}")
    public ResponseEntity<?> getInvoicespage(
            @PathVariable Long buyerId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Integer trangThai,  // Use Integer for nullable parameter
            Pageable pageable) {

        // Gọi service để lấy danh sách hóa đơn
        Page<InvoiceDto> invoiceDtos = invoiceService.getInvoicespage(buyerId, startDate, endDate, trangThai, pageable);


        return ResponseEntity.ok(Map.of("status", "success", "data", invoiceDtos));
    }
    @GetMapping("/count/current")
    public long getCurrentMonthInvoiceCount() {
        return invoiceService.countInvoices();
    }

    // Endpoint to count invoices for a specific month and year
    @GetMapping("/count")
    public long getCountInvoices(
            @RequestParam int month,
            @RequestParam int year) {
        return invoiceService.countInvoices(month, year);
    }

    @GetMapping("cancel/{id}")
    public ResponseEntity<?> cancelinvoice(@PathVariable Long id) {
        boolean isCancelInvoice = invoiceService.cancelInvoice(id);
        if (isCancelInvoice) {
            return ResponseEntity.ok(Map.of("message", "OK"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("status", "error"));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Invoice>> searchInvoices(
            @RequestParam(required = false) String paymentmethod,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String invoiceCode,
            @RequestParam(required = false) String phonenumber,
            @RequestParam(required = false) String deliveryAddress,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate,
            Pageable pageable) {
        Page<Invoice> invoices = invoiceService.getInvoices(paymentmethod, status, invoiceCode,
                phonenumber,
                deliveryAddress, startDate,
                endDate, pageable);
        return ResponseEntity.ok(invoices);
    }

    @PutMapping("updatequantity/{id}")
    public ResponseEntity<?> updatequantity(@PathVariable Long id, @RequestBody @Valid Invoice invoice,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Map<String, String>> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                Map<String, String> error = new HashMap<>();
                error.put("field", fieldError.getField());
                error.put("message", fieldError.getDefaultMessage());
                errors.add(error);
            }
            return ResponseEntity.badRequest().body(Map.of("status", "error", "errors", errors));
        }
        return ResponseEntity.ok(
                Map.of("status", "success", "message", invoiceService.updatequantity(id, invoice)));
    }

    @PutMapping("/waiting/{id}")
    public ResponseEntity<?> waiting(@PathVariable Long id,@RequestBody Long usellerid) {
        boolean is = invoiceService.waitingInvoice(id,usellerid);
        if (!is) {
            return ResponseEntity.badRequest().body(Map.of("Error", "Số Lượng Sản Phẩm Không Đủ"));
        }
        return ResponseEntity.ok(Map.of("success", true));
    }
    @GetMapping("/getInvoice/{invoiceCode}")
    public ResponseEntity<?> getInvoiceCode(@PathVariable String invoiceCode) {
        return invoiceService.findInvoicesByInvoiceCode(invoiceCode);
    }
}
