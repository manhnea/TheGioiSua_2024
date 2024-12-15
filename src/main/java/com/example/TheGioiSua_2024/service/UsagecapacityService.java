package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.Validator.UsagecapacityValidator;
import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.Usagecapacity;
import com.example.TheGioiSua_2024.repository.UsagecapacityRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IUsagecapacityService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Service
public class UsagecapacityService implements IUsagecapacityService {

    @Autowired
    private UsagecapacityRepository usagecapacityRepository;
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private logService logService;

    @Override
    public List<Usagecapacity> getAllUsagecapacity() {
        return usagecapacityRepository.findAll();
    }

    @Override
    public ResponseEntity<?> addUsagecapacity(String token, Usagecapacity usagecapacity) {

        String error = UsagecapacityValidator.validateUsagecapacity(usagecapacity);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        if (usagecapacityRepository.existsByCapacityAndUnit(usagecapacity.getCapacity(), usagecapacity.getUnit())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Đơn Vị Đóng Gói Đã Tồn Tại"));
        }
        usagecapacity.setStatus(1);
        String username = jwtUtilities.extractUsername(token);
        Log log = new Log(); // Tạo log
        log.setAction("Thêm đơn vị sử dụng");
        log.setDescription(
                String.format("Thêm đơn vị sử dụng %s với dung lượng %d", usagecapacity.getUnit(),
                        usagecapacity.getCapacity()));
        logService.saveLog(username, log);

        usagecapacityRepository.save(usagecapacity);
        return ResponseEntity.ok(Map.of("success", "Thành Công"));
    }

    @Override
    public ResponseEntity<?> updateUsagecapacity(String token, Long id, Usagecapacity usagecapacity) {
         String error = UsagecapacityValidator.validateUsagecapacity(usagecapacity);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        if (usagecapacityRepository.existsByCapacityAndUnit(usagecapacity.getCapacity(), usagecapacity.getUnit())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Đơn Vị Đóng Gói Đã Tồn Tại"));
        }
        Usagecapacity existingUsagecapacity = usagecapacityRepository.findById(id).orElseThrow();
        String username = jwtUtilities.extractUsername(token);
        Log log = new Log(); // Tạo log
        log.setDescription(String.format("Cập nhật đơn vị sử dụng %s thành %s với dung lượng %d",
                existingUsagecapacity.getUnit(), usagecapacity.getUnit(), usagecapacity.getCapacity()));
        existingUsagecapacity.setCapacity(usagecapacity.getCapacity());
        existingUsagecapacity.setUnit(usagecapacity.getUnit());
        existingUsagecapacity.setStatus(Status.Active);
        log.setAction("Cập nhật đơn vị sử dụng");
        usagecapacityRepository.save(existingUsagecapacity);
        logService.saveLog(username, log);
        return ResponseEntity.ok(Map.of("success", "Thành Công"));
    }

    @Override
    public ResponseEntity<?> deleteUsagecapacity(String token, Long id) {
        String username = jwtUtilities.extractUsername(token);
        Log log = new Log(); // Tạo log
        Usagecapacity existingUsagecapacity = usagecapacityRepository.findById(id).orElseThrow();
        if (existingUsagecapacity.getStatus() == Status.Delete) {
            existingUsagecapacity.setStatus(Status.Active);
            log.setAction("Khôi phục đơn vị sử dụng");
            log.setDescription(
                    String.format("Khôi phục đơn vị sử dụng %s", existingUsagecapacity.getUnit()));
            usagecapacityRepository.save(existingUsagecapacity);
            return ResponseEntity.ok(Map.of("success", "Thành Công"));
        } else {
            existingUsagecapacity.setStatus(Status.Delete);
            log.setAction("Xóa đơn vị sử dụng");
            log.setDescription(String.format("Xóa đơn vị sử dụng %s", existingUsagecapacity.getUnit()));
            usagecapacityRepository.save(existingUsagecapacity);
            return ResponseEntity.ok(Map.of("success", "Thành Công"));
        }
    }

    @Override
    public Usagecapacity getUsagecapacityById(Long id) {
        return usagecapacityRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<Usagecapacity> getUsagecapacityPage(Pageable pageable) {
        return usagecapacityRepository.getUsagecapacityPage(pageable);
    }

//  @Override
//  public Page<Usagecapacity> getUsagecapacityPageByCapacity(int capacity,String unit, Pageable pageable) {
//    return usagecapacityRepository.findByCapacityContaining(capacity,unit, pageable);
//  }
}
