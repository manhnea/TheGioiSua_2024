package com.example.TheGioiSua_2024.service.impl;

import com.example.TheGioiSua_2024.entity.Userinvoice;

import java.util.List;

public interface IUserinvoiceService {
    List<Userinvoice> getUserinvoiceList();

    String saveUserinvoice(Userinvoice userinvoice);

    String updateUserinvoice(Long id, Userinvoice userinvoice);

    void deleteUserinvoice(Long id, Userinvoice userinvoice);

    Userinvoice getUserinvoiceById(Long id);
}
