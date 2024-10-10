package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.repository.MilkdetailRepository;
import com.example.TheGioiSua_2024.service.impl.IMilkdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilkdetailService implements IMilkdetailService {
    @Autowired
    private MilkdetailRepository milkdetailRepository;


    @Override
    public List<Milkdetail> getAll() {
        return milkdetailRepository.findAll();
    }

    @Override
    public Milkdetail add(Milkdetail milkdetail) {
        return milkdetailRepository.save(milkdetail);
    }

    @Override
    public Milkdetail update(Long id, Milkdetail milkdetail) {
        Optional<Milkdetail> optionalMilkdetail = milkdetailRepository.findById(id);
        if (optionalMilkdetail.isPresent()) {
            Milkdetail existingMilkdetail = optionalMilkdetail.get();
            existingMilkdetail.setMilktaste(milkdetail.getMilktaste());
            existingMilkdetail.setStockquantity(milkdetail.getStockquantity());
            existingMilkdetail.setProduct(milkdetail.getProduct());
            existingMilkdetail.setPrice(milkdetail.getPrice());
            existingMilkdetail.setExpirationdate(milkdetail.getExpirationdate());
            existingMilkdetail.setDescription(milkdetail.getDescription());
            existingMilkdetail.setStockquantity(milkdetail.getStockquantity());
            existingMilkdetail.setStatus(1);
            existingMilkdetail.setContainer(milkdetail.getContainer());
            existingMilkdetail.setSize(milkdetail.getSize());
            existingMilkdetail.setPackagingunit(milkdetail.getPackagingunit());
            existingMilkdetail.setUsagecapacity(milkdetail.getUsagecapacity());
            return milkdetailRepository.save(existingMilkdetail);
        } else {
            throw new RuntimeException("Milkdetail with id " + id + " not found.");
        }
    }

    @Override
    public Milkdetail delete(Long id, Milkdetail milkdetail) {
        Optional<Milkdetail> optionalMilkdetail = milkdetailRepository.findById(id);
        if (optionalMilkdetail.isPresent()) {
            Milkdetail existingMilkdetail = optionalMilkdetail.get();
            existingMilkdetail.setStatus(0);
            return milkdetailRepository.save(existingMilkdetail);
        } else {
            throw new RuntimeException("Milkdetail with id " + id + " not found.");
        }
    }
}
