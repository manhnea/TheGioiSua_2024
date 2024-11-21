/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.dto;

import com.example.TheGioiSua_2024.entity.Invoicedetail;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.entity.Userinvoice;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Hieu
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceDto {

    Long invoiceID;
    String invoiceCode;
    String nguoiMua;
    String nguoiBan;
    LocalDateTime ngayTao;
    String deliveryaddress;
    String phonenumber;
    String paymentmethod;
    String voucherCode;
    int sotienGiamGia;
    int tongTien;
    List<Invoicedetail> invoiceDetails;
    User nguoiTao;
    int trangThai;

    public InvoiceDto(Long invoiceID, String invoiceCode, String nguoiMua, String nguoiBan, LocalDateTime ngayTao, String deliveryaddress, String phonenumber, String paymentmethod, String voucherCode, int sotienGiamGia, int tongTien, int trangThai) {
        this.invoiceID = invoiceID;
        this.invoiceCode = invoiceCode;
        this.nguoiMua = nguoiMua;
        this.nguoiBan = nguoiBan;
        this.ngayTao = ngayTao;
        this.deliveryaddress = deliveryaddress;
        this.phonenumber = phonenumber;
        this.paymentmethod = paymentmethod;
        this.voucherCode = voucherCode;
        this.sotienGiamGia = sotienGiamGia;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "InvoiceDto{"
                + "invoiceID=" + invoiceID
                + ", invoiceCode='" + invoiceCode + '\''
                + ", nguoiMua='" + nguoiMua + '\''
                + ", nguoiBan='" + nguoiBan + '\''
                + ", ngayTao=" + ngayTao
                + ", deliveryaddress='" + deliveryaddress + '\''
                + ", phonenumber='" + phonenumber + '\''
                + ", paymentmethod='" + paymentmethod + '\''
                + ", voucherCode='" + voucherCode + '\''
                + ", sotienGiamGia=" + sotienGiamGia
                + ", tongTien=" + tongTien
                + ", invoiceDetails=" + invoiceDetails
                + ", nguoiTao=" + nguoiTao
                + ", trangThai=" + trangThai
                + '}';
    }
}
