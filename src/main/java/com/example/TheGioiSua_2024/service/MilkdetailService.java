package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.*;
import com.example.TheGioiSua_2024.repository.*;
import com.example.TheGioiSua_2024.service.impl.IMilkdetailService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
//        String milkdetailcode = milkdetail.getMilkdetailcode().trim();
        Integer maxId = milkdetailRepository.findMaxId();

        if (maxId == null) {
            maxId = 1;  // Nếu bảng trống thì bắt đầu từ 1
        } else {
            maxId++;
        }

        // Tạo mã chi tiết sản phẩm theo định dạng "MD" + 3 số
        String milkdetailcode = String.format("MD%03d", maxId);
        milkdetail.setMilkdetailcode(milkdetailcode);
        milkdetail.setStatus(Status.Active);
        milkdetailRepository.save(milkdetail);
        return "Thêm thành công";
    }

    @Override
    public String update(Long id, Milkdetail milkdetail) {
        try {
            Milkdetail milkdetailnew = milkdetailRepository.findById(id).orElseThrow(() -> new RuntimeException("MilkDetail Không Tồn Tại"));
            Product product = productRepository.findById(milkdetail.getProduct().getId()).orElseThrow(() -> new RuntimeException("Sản Phẩm Không Tồn Tại"));
            Milktaste milktaste = milktasteRepository.findById(milkdetail.getMilkTaste().getId()).orElseThrow(() -> new RuntimeException("Vị Sữa Không Tồn Tại"));
            Packagingunit packagingunit = packagingunitRepository.findById(milkdetail.getPackagingunit().getId()).orElseThrow(() -> new RuntimeException("Đơn Vị Đóng Gói Không Tồn Tại"));
            Usagecapacity usagecapacity = usagecapacityRepository.findById(milkdetail.getUsageCapacity().getId()).orElseThrow(() -> new RuntimeException("Dung Tích Sử Dụng Không Tồn Tại"));
            milkdetailnew.setProduct(product);
            milkdetailnew.setMilkTaste(milktaste);
            milkdetailnew.setPackagingunit(packagingunit);
            milkdetailnew.setUsageCapacity(usagecapacity);
            milkdetailnew.setPrice(milkdetail.getPrice());
            milkdetailnew.setExpirationdate(milkdetail.getExpirationdate());
            milkdetailnew.setDescription(milkdetail.getDescription());
            milkdetailnew.setStockquantity(milkdetail.getStockquantity());
            milkdetailnew.setImgUrl(milkdetail.getImgUrl());
            milkdetailnew.setStatus(Status.Active);
            milkdetailRepository.save(milkdetailnew);
            return "Sửa thành công";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String delete(Long id) {
        Milkdetail milkdetailnew = milkdetailRepository.findById(id).get();
        if (milkdetailnew.getStatus() == Status.Delete) {
            milkdetailnew.setStatus(Status.Active);
            milkdetailRepository.save(milkdetailnew);
            return "Khôi phục thành công";
        } else {
            milkdetailnew.setStatus(Status.Delete);
            milkdetailRepository.save(milkdetailnew);
            return "Xóa thành công";
        }

    }

    @Override
    public Milkdetail getById(Long id) {
        return milkdetailRepository.findById(id).get();
    }

    @Override
    public Page<MilkDetailDto> getPageMilkDetail(Pageable pageable, MilkDetailDto milkDetailDto) {
        return milkdetailRepository.getPageMilkDetail(
                pageable,
                milkDetailDto.getMilktypeID(),
                milkDetailDto.getMilkBrandID(),
                milkDetailDto.getPackagingunitID(),
                milkDetailDto.getMilktasteID(),
                milkDetailDto.getProductID(),
                milkDetailDto.getTargetuserID(),
                milkDetailDto.getUsagecapacityID());
    }

    @Override
//    public MilkDetailDto getMilkDetail(MilkDetailDto milkDetailDto) {
//        return milkdetailRepository.getMilkDetail(
//                milkDetailDto.getMilktypeID(),
//                milkDetailDto.getMilkBrandID(),
//                milkDetailDto.getPackagingunitID(),
//                milkDetailDto.getMilktasteID(),
//                milkDetailDto.getProductID(),
//                milkDetailDto.getTargetuserID(),
//                milkDetailDto.getUsagecapacityID());
//    }
    public MilkDetailDto getMilkDetail(Long packagingunitID, Long milktasteID, Long productID, Long usagecapacityID) {
        return milkdetailRepository.getMilkDetail(packagingunitID, milktasteID, productID, usagecapacityID);
    }

}
