/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.api;

import com.example.TheGioiSua_2024.dto.InvoiceDTO;
import com.example.TheGioiSua_2024.model.Products;
import com.example.TheGioiSua_2024.repository.ProductsRepository;
import com.example.TheGioiSua_2024.service.InvoiceService;
import com.example.TheGioiSua_2024.util.ThongBao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hieu
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    InvoiceService invoiceService;
    @GetMapping("/all")
    public InvoiceDTO getAll() {
        return invoiceService.get_Invoice(1);
    }

    @PostMapping("/add")
    public String ThemSanPham(@RequestBody Products input_Product) {
        try {
            productsRepository.save(input_Product);
            return ThongBao.ThanhCong;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
