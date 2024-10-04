/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author Hieu
 */
@Entity
public class VaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String TenVaiTro;
    private int TrangThai;

    public VaiTro() {
    }

    public VaiTro(int ID, String TenVaiTro, int TrangThai) {
        this.ID = ID;
        this.TenVaiTro = TenVaiTro;
        this.TrangThai = TrangThai;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenVaiTro() {
        return TenVaiTro;
    }

    public void setTenVaiTro(String TenVaiTro) {
        this.TenVaiTro = TenVaiTro;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
