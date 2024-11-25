package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.repository.VoucherRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IMilktypeService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilktypeService implements IMilktypeService {
    @Autowired
    MilktypeRepository milktypeRepository;
    @Autowired
    private logService logService;
    @Autowired
    private JwtUtilities jwtUtilities;
    @Override
    public String AddMilktype(String token,MilkType milktype) {
        String trimmedName = milktype.getMilkTypename().trim();
        milktype.setMilkTypename(trimmedName);
        Optional<MilkType> existingContainer = getMilkTypeByName(trimmedName);
        if (existingContainer.isPresent()) {
            return "Tên này đã tồn tại.";
        }
        milktype.setStatus(Status.Active);
        String username = jwtUtilities.extractUsername(token);

        String message = String.format(
                "Tên loại sữa: %s, Mô tả: %s, Trạng thái: %s",
                milktype.getMilkTypename(),
                milktype.getDescription(),
                milktype.getStatus()
        );

        Log log = new Log(); // Tạo log
        log.setAction("Thêm voucher");
        log.setDescription(message);
        logService.saveLog(username, log);
        milktypeRepository.save(milktype);
        return "Them Thanh Cong";
    }
    @Override
    public String UpdateMilktype(Long id, MilkType milktype) {
        MilkType existingMilkType = milktypeRepository.findById(id).orElseThrow();
        String currentMilkTypeName = existingMilkType.getMilkTypename();
        if (currentMilkTypeName.equals(milktype.getMilkTypename())) {
            existingMilkType.setDescription(milktype.getDescription());
            existingMilkType.setStatus(Status.Active);
            milktypeRepository.save(existingMilkType);
            return "Cập nhật mô tả loại sữa sữa thành công.";
        } else if (milktypeRepository.findByMilkTypename(milktype.getMilkTypename()).isPresent()) {
            return "loại sữa sữa này đã tồn tại.";
        }
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        milktype1.setDescription(milktype.getDescription());
        milktype1.setMilkTypename(milktype.getMilkTypename());
         milktypeRepository.save(milktype1);
         return "Sua Thanh Cong";
    }
    @Override
    public String DeleteMilktype(String token,Long id) {
        String username = jwtUtilities.extractUsername(token);
        MilkType milktype1 = milktypeRepository.findById(id).orElseThrow();
        if (milktype1.getStatus() == Status.Delete) {
            milktype1.setStatus(Status.Active);
            Log log = new Log(); // Tạo log
            log.setAction("Khôi phục laoi sua");
            log.setDescription(String.format(milktype1.getMilkTypename()));
            logService.saveLog(username, log);
            milktypeRepository.save(milktype1);
            return "Khôi phục loại sữa thành công!";
        } else {
            milktype1.setStatus(Status.Delete);
            Log log = new Log(); // Tạo log
            log.setAction("Xoa laoi sua");
            log.setDescription(String.format(milktype1.getMilkTypename()));
            logService.saveLog(username, log);
            milktypeRepository.save(milktype1);
            return "Xóa Thanh Cong";
        }
    }

    @Override
    public Optional<MilkType> getMilkTypeByName(String milktypename) {
        return milktypeRepository.findByMilkTypename(milktypename);
    }
    @Override
    public MilkType GetMilktypeById(Long id) {
        return milktypeRepository.findById(id).orElseThrow();
    }
    @Override
    public List<MilkType> GetAllMilktype() {
        return milktypeRepository.findAll();
    }


}
