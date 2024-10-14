package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Invoice;
import com.example.TheGioiSua_2024.entity.User;
import com.example.TheGioiSua_2024.entity.Userinvoice;
import com.example.TheGioiSua_2024.repository.InvoiceRepository;
import com.example.TheGioiSua_2024.repository.UserRepository;
import com.example.TheGioiSua_2024.repository.UserinvoiceRepository;
import com.example.TheGioiSua_2024.service.impl.IUserinvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserinvoiceService implements IUserinvoiceService {

    @Autowired
    private UserinvoiceRepository userinvoiceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Userinvoice> getUserinvoiceList() {
        return userinvoiceRepository.findAll();
    }

    @Override
    public String saveUserinvoice(Userinvoice userinvoice) {
        userinvoice.setStatus(1);
        userinvoiceRepository.save(userinvoice);
        return "Userinvoice added successfully";
    }

    @Override
    public String updateUserinvoice(Long id, Userinvoice userinvoice) {
        Userinvoice existingUserinvoice = userinvoiceRepository.findById(id).orElseThrow();
        User user = userRepository.findById(existingUserinvoice.getUser().getId()).orElseThrow();
        Invoice invoice = invoiceRepository.findById(existingUserinvoice.getInvoice().getId()).orElseThrow();
        existingUserinvoice.setUser(user);
        existingUserinvoice.setInvoice(invoice);
        existingUserinvoice.setStatus(1);
        return "Userinvoice updated successfully!";
    }

    @Override
    public void deleteUserinvoice(Long id, Userinvoice userinvoice) {
        Userinvoice existingUserinvoice = userinvoiceRepository.findById(id).orElseThrow();
        existingUserinvoice.setStatus(0);
        userinvoiceRepository.save(existingUserinvoice);
    }
}
