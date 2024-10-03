/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.dto;

/**
 *
 * @author Administrator
 */
public class InvoiceDTO {
    public int id_Invoice;
    public double price;
    public String product_name;
    public String create_by;

    public InvoiceDTO(int id_Invoice, double price, String product_name, String create_by) {
        this.id_Invoice = id_Invoice;
        this.price = price;
        this.product_name = product_name;
        this.create_by = create_by;
    }
    
}
