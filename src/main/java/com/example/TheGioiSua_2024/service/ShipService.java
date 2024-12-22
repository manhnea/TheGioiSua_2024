/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.ShippingstatusDto;
import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.InvoiceLog;
import com.example.TheGioiSua_2024.repository.InvoiceLogRepository;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.service.impl.IShipService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hieu
 */
@Service
public class ShipService implements IShipService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    InvoiceLogRepository invoiceLogRepository;

    @Override
    public ResponseEntity<?> getShippingStatus(ShippingstatusDto shippingstatusDto) {
        System.out.println(shippingstatusDto.toString());
        int Istatus = Integer.parseInt(shippingstatusDto.getStatus());
        InvoiceLog invoiceLog = new InvoiceLog();
        Invoice invoice = invoiceRepository.findbycode(shippingstatusDto.getOrder_id());
        if (Istatus == Status.Took) {
            invoice.setStatus(Status.Took);
            invoiceLog.setStatus(Status.Took);
        } else if (Istatus == Status.Delivery) {
            invoice.setStatus(Status.Delivery);
            invoiceLog.setStatus(Status.Delivery);
        } else if (Istatus == Status.Complete) {
            invoice.setStatus(Status.Complete);
            invoiceLog.setStatus(Status.Complete);
        }else if (Istatus == Status.DELIVERY_FAILED) {
            invoice.setStatus(Status.DELIVERY_FAILED);
            invoiceLog.setStatus(Status.DELIVERY_FAILED);
        }
        invoiceRepository.save(invoice);
        invoiceLog.setInvoice(invoice);
        invoiceLogRepository.save(invoiceLog);
        return ResponseEntity.ok("ok");
    }

}