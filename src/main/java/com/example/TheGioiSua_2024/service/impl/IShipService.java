/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.dto.ShippingstatusDto;
import org.springframework.http.ResponseEntity;


/**
 *
 * @author Hieu
 */
public interface IShipService {
    ResponseEntity<?> getShippingStatus(ShippingstatusDto shippingstatusDto);
}
