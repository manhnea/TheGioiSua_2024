/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.model.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hieu
 */
@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long>{
    NguoiDung timKiemVoiTenDangNhap(String TenDangNhap);
    NguoiDung timKiemVoiId(Long ID);
}
