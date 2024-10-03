/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.InvoiceDTO;
import com.example.TheGioiSua_2024.model.Products;
import com.example.TheGioiSua_2024.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class InvoiceService {
    @Autowired
    ProductsRepository productsRepository;
    InvoiceDTO invoiceDTO = null;
    public InvoiceDTO get_Invoice(int id_kh){
        Products products = productsRepository.findById(id_kh).orElseThrow();
        invoiceDTO = new InvoiceDTO(
                products.getProduct_id(), 
                products.getPrice(), 
                products.getProduct_name(), 
                "Hieu dzpro"
        );
        return invoiceDTO;
    }
}
