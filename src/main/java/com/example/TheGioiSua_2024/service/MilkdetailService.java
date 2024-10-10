package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.*;
import com.example.TheGioiSua_2024.repository.*;
import com.example.TheGioiSua_2024.service.impl.IMilkdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilkdetailService implements IMilkdetailService {
    @Autowired
    private MilkdetailRepository milkdetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ContainerRepository containerRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private MilktasteRepository milktasteRepository;
    @Autowired
    private PackagingunitRepository packagingunitRepository;
    @Autowired
    private UsagecapacityRepository usagecapacityRepository;


    @Override
    public List<Milkdetail> getAll() {
        return milkdetailRepository.findAll();
    }

    @Override
    public String add(Milkdetail milkdetail) {
        milkdetail.setStatus(1);
        milkdetailRepository.save(milkdetail);
        return "Thêm thành công";
    }

    @Override
    public String update(Long id, Milkdetail milkdetail) {
        Milkdetail milkdetail1 = milkdetailRepository.findById(id).get();
        Product product = productRepository.findById(milkdetail.getProduct().getId()).get();
        Size size = sizeRepository.findById(milkdetail.getSize().getId()).get();
        Container container = containerRepository.findById(milkdetail.getContainer().getId()).get();
        Milktaste milktaste = milktasteRepository.findById(milkdetail.getMilktaste().getId()).get();
        Packagingunit packagingunit = packagingunitRepository.findById(milkdetail.getPackagingunit().getId()).get();
        Usagecapacity usagecapacity = usagecapacityRepository.findById(milkdetail.getUsagecapacity().getId()).get();
        milkdetail1.setProduct(product);
        milkdetail1.setSize(size);
        milkdetail1.setContainer(container);
        milkdetail1.setMilktaste(milktaste);
        milkdetail1.setPackagingunit(packagingunit);
        milkdetail1.setUsagecapacity(usagecapacity);
        milkdetail1.setPrice(milkdetail.getPrice());
        milkdetail1.setExpirationdate(milkdetail.getExpirationdate());
        milkdetail1.setDescription(milkdetail.getDescription());
        milkdetail1.setStockquantity(milkdetail.getStockquantity());
        milkdetail1.setStatus(1);
        milkdetailRepository.save(milkdetail1);
        return "Sửa thành công";
    }

    @Override
    public String delete(Long id, Milkdetail milkdetail) {
        Milkdetail milkdetail1 = milkdetailRepository.findById(id).get();
        milkdetail1.setStatus(0);
        milkdetailRepository.save(milkdetail1);
        return "Xóa thành công";
    }

}
