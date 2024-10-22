package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.*;
import com.example.TheGioiSua_2024.repository.*;
import com.example.TheGioiSua_2024.service.impl.IMilkdetailService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class MilkdetailService implements IMilkdetailService {
    @Autowired
    private MilkdetailRepository milkdetailRepository;
    @Autowired
    private ProductRepository productRepository;
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
        String milkdetailcode = milkdetail.getMilkdetailcode().trim();
//        Integer maxId = milkdetailRepository.findMaxId();
//
//        if (maxId == null) {
//            maxId = 1;  // Nếu bảng trống thì bắt đầu từ 1
//        } else {
//            maxId++;
//        }
//
//        // Tạo mã chi tiết sản phẩm theo định dạng "MD" + 3 số
//        String milkdetailcode = String.format("MD%03d", maxId);
        milkdetail.setMilkdetailcode(milkdetailcode);
        if (milkdetailRepository.existsBymilkdetailcode(milkdetailcode).isPresent()) {
            return "Mã sản phẩm đã tồn tại.";
        }
        milkdetail.setStatus(Status.Active);
        milkdetailRepository.save(milkdetail);
        return "Thêm thành công";
    }

    @Override
    public String update(Long id, Milkdetail milkdetail) {
        Milkdetail milkdetail1 = milkdetailRepository.findById(id).get();
        Product product = productRepository.findById(milkdetail1.getProduct().getId()).get();
        Milktaste milktaste = milktasteRepository.findById(milkdetail1.getMilkTaste().getId()).get();
        Packagingunit packagingunit = packagingunitRepository.findById(milkdetail1.getPackagingunit().getId()).get();
        Usagecapacity usagecapacity = usagecapacityRepository.findById(milkdetail1.getUsageCapacity().getId()).get();
        String milkdetailcode = milkdetail.getMilkdetailcode().trim();
        milkdetail.setMilkdetailcode(milkdetailcode);
        if (milkdetailRepository.existsBymilkdetailcode(milkdetailcode).isPresent()) {
            return "Mã sản phẩm đã tồn tại.";
        }
        milkdetail1.setProduct(product);
        milkdetail1.setMilkTaste(milktaste);
        milkdetail1.setPackagingunit(packagingunit);
        milkdetail1.setUsageCapacity(usagecapacity);
        milkdetail1.setMilkdetailcode(milkdetail.getMilkdetailcode());
        milkdetail1.setPrice(milkdetail.getPrice());
        milkdetail1.setExpirationdate(milkdetail.getExpirationdate());
        milkdetail1.setDescription(milkdetail.getDescription());
        milkdetail1.setStockquantity(milkdetail.getStockquantity());
        milkdetail1.setStatus(Status.Active);
        milkdetailRepository.save(milkdetail1);
        return "Sửa thành công";
    }

    @Override
    public String delete(Long id) {
        Milkdetail milkdetail1 = milkdetailRepository.findById(id).get();
        if(milkdetail1.getStatus() == Status.Delete) {
            milkdetail1.setStatus(Status.Active);
            milkdetailRepository.save(milkdetail1);
            return "Khôi phục thành công";
        }else {
            milkdetail1.setStatus(Status.Delete);
            milkdetailRepository.save(milkdetail1);
            return "Xóa thành công";
        }

    }

    @Override
    public Milkdetail getById(Long id) {
        return milkdetailRepository.findById(id).get();
    }

    @Override
    public Page<MilkDetailDto> getPageMilkDetail(Pageable pageable) {
        return milkdetailRepository.getPageMilkDetail(pageable);
    }

}
