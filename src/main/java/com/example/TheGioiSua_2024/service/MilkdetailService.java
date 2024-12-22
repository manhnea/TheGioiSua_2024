package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.Validator.MilkTasteValidator;
import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.*;
import com.example.TheGioiSua_2024.repository.*;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IMilkdetailService;
import com.example.TheGioiSua_2024.util.MilkdetailValidator;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MilkdetailService implements IMilkdetailService {

    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private logService logService;
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
    public String checkDuplicatemilkdetail(Long getProduct, Long getMilkTaste, Long getPackagingunit, Long getUsageCapacity) {
        // Check if Usagecapacity with the same capacity and unit already exists
        return null; // No duplicates found
    }

    @Override
    public ResponseEntity<?> add(String token, Milkdetail milkdetail) {
        // Step 1: Validate the milkdetail object
        String error = MilkdetailValidator.validateMilkdetail(milkdetail);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        // Step 2: Check if the milkdetail already exists
        if (milkdetailRepository.findByIds(
                milkdetail.getProduct().getId(),
                milkdetail.getMilkTaste().getId(),
                milkdetail.getPackagingunit().getId(),
                milkdetail.getUsageCapacity().getId()
        ).isPresent()) {
            // Return error if the milkdetail already exists
            return ResponseEntity.badRequest().body(Map.of("error", "Chi tiết sản phẩm này đã tồn tại."));
        }
        // Step 3: Get the max ID from the repository (or start from 1 if empty)
        Integer maxId = milkdetailRepository.findMaxId();
        if (maxId == null) {
            maxId = 1;  // If no records exist, start from ID 1
        } else {
            maxId++;  // Increment the max ID for the new Milkdetail
        }

        // Step 4: Generate the milkdetail code (e.g., MD001, MD002, etc.)
        String milkdetailcode = String.format("MD%03d", maxId);
        milkdetail.setMilkdetailcode(milkdetailcode);  // Set the generated milkdetail code
        milkdetail.setStatus(Status.Active);  // Set the status to Active by default
        // Step 5: Extract the username from the token (for logging purposes)
        String username = jwtUtilities.extractUsername(token);
        // Step 6: Prepare the log message
        String message = String.format(
                "Tên sản phẩm: %s, Mô tả: %s, Đơn vị đóng gói: %s, Hương vị: %s, "
                + "Số lượng trong kho: %d, URL hình ảnh: %s, Dung tích sử dụng: %s, "
                + "Hạn sử dụng: %s, Trạng thái: %s, Mã chi tiết sữa: %s, Giá: %.2f",
                milkdetail.getProduct(),
                milkdetail.getDescription(),
                milkdetail.getPackagingunit(),
                milkdetail.getMilkTaste(),
                milkdetail.getStockquantity(),
                milkdetail.getImgUrl(),
                milkdetail.getUsageCapacity(),
                milkdetail.getShelflifeofmilk(),
                milkdetail.getStatus(),
                milkdetail.getMilkdetailcode(),
                milkdetail.getPrice()
        );

        // Step 7: Log the action
        Log log = new Log();  // Create a new log entry
        log.setAction("Thêm chi tiết sản phẩm");
        log.setDescription(message);
        logService.saveLog(username, log);  // Save the log with username
        // Step 8: Save the new Milkdetail entity in the repository
        milkdetailRepository.save(milkdetail);

        // Step 9: Return a success message
        return ResponseEntity.ok(Map.of("status", "success", "message", "Thêm chi tiết sản phẩm thành công."));
    }
//a

    @Override
    public Page<Milkdetail> filterMilkdetails(Long productId, String codeMilkDetail, Long milkTasteId, Long packagingUnitId, Long usageCapacityId, Long milkBrandId, Long targetUserId, Long milkTypeId, Pageable pageable) {
        return milkdetailRepository.filterMilkdetails(productId, codeMilkDetail, milkTasteId, packagingUnitId, usageCapacityId, milkBrandId, targetUserId, milkTypeId, pageable);
    }

    @Override
    public ResponseEntity<?> update(String token, Long id, Milkdetail milkdetail) {
        Milkdetail existingMilkDetail = milkdetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết sữa không tồn tại với ID: " + id));

        String username = jwtUtilities.extractUsername(token);

        // Kiểm tra và lấy từng thực thể liên quan
        Product product = productRepository.findById(milkdetail.getProduct().getId())
                .orElseThrow(() -> new RuntimeException(
                "Sản phẩm không tồn tại với ID: " + milkdetail.getProduct().getId()));

        Milktaste milktaste = milktasteRepository.findById(milkdetail.getMilkTaste().getId())
                .orElseThrow(() -> new RuntimeException(
                "Hương vị không tồn tại với ID: " + milkdetail.getMilkTaste().getId()));

        Packagingunit packagingunit = packagingunitRepository.findById(milkdetail.getPackagingunit().getId())
                .orElseThrow(() -> new RuntimeException(
                "Đơn vị đóng gói không tồn tại với ID: " + milkdetail.getPackagingunit().getId()));

        Usagecapacity usagecapacity = usagecapacityRepository.findById(milkdetail.getUsageCapacity().getId())
                .orElseThrow(() -> new RuntimeException(
                "Dung tích sử dụng không tồn tại với ID: " + milkdetail.getUsageCapacity().getId()));

        // Lưu thông tin cũ để ghi log
        String oldInfo = String.format(
                "Sản phẩm: %s, Vị sữa: %s, Đơn vị đóng gói: %s, Dung tích: %s, Mô tả: %s, Ảnh: %s, Hạn sử dụng: %s, Giá: %.2f",
                existingMilkDetail.getProduct().getProductname(),
                existingMilkDetail.getMilkTaste().getMilktastename(),
                existingMilkDetail.getPackagingunit().getPackagingunitname(),
                existingMilkDetail.getUsageCapacity().getUnit(),
                existingMilkDetail.getDescription(),
                existingMilkDetail.getImgUrl(),
                existingMilkDetail.getShelflifeofmilk(),
                existingMilkDetail.getPrice()
        );

        // Validate the input milkdetail
        String error = MilkdetailValidator.validateMilkdetail(milkdetail);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }

        // Check if the new data is the same as the old data
        if (existingMilkDetail.getProduct().getId().equals(milkdetail.getProduct().getId())
                && existingMilkDetail.getMilkTaste().getId().equals(milkdetail.getMilkTaste().getId())
                && existingMilkDetail.getPackagingunit().getId().equals(milkdetail.getPackagingunit().getId())
                && existingMilkDetail.getUsageCapacity().getId().equals(milkdetail.getUsageCapacity().getId())) {

            // If data is the same, just update the stock quantity and status
            existingMilkDetail.setStatus(milkdetail.getStockquantity() <= 0 ? 0 : 1);
            existingMilkDetail.setDescription(milkdetail.getDescription());
            existingMilkDetail.setImgUrl(milkdetail.getImgUrl());
            existingMilkDetail.setShelflifeofmilk(milkdetail.getShelflifeofmilk());
            existingMilkDetail.setPrice(milkdetail.getPrice());
            existingMilkDetail.setStockquantity(milkdetail.getStockquantity());

            // Save the updated milk detail
            milkdetailRepository.save(existingMilkDetail);

            // Lưu thông tin mới để ghi log
            String newInfo = String.format(
                    "Sản phẩm: %s, Vị sữa: %s, Đơn vị đóng gói: %s, Dung tích: %s, Mô tả: %s, Ảnh: %s, Hạn sử dụng: %s, Giá: %.2f",
                    product.getProductname(),
                    milktaste.getMilktastename(),
                    packagingunit.getPackagingunitname(),
                    usagecapacity.getUnit(),
                    milkdetail.getDescription(),
                    milkdetail.getImgUrl(),
                    milkdetail.getShelflifeofmilk(),
                    milkdetail.getPrice()
            );

            // Log the action
            Log log = new Log();
            log.setAction("Cập nhật chi tiết sữa");
            log.setDescription(String.format(
                    "Chi tiết sữa đã được cập nhật. Thông tin cũ: [%s]. Thông tin mới: [%s].",
                    oldInfo,
                    newInfo
            ));
            logService.saveLog(username, log);

            return ResponseEntity.ok("Cập nhật thành công");
        }
        if (milkdetailRepository.findByIds(
                milkdetail.getProduct().getId(),
                milkdetail.getMilkTaste().getId(),
                milkdetail.getPackagingunit().getId(),
                milkdetail.getUsageCapacity().getId()
        ).isPresent()) {
            // Return error if the milkdetail already exists
            return ResponseEntity.badRequest().body(Map.of("error", "Chi tiết sản phẩm này đã tồn tại."));
        }

        // If we reach here, that means something changed (like product or taste)
        // Update the status based on stock quantity
        existingMilkDetail.setStatus(milkdetail.getStockquantity() <= 0 ? 0 : 1);

        // Update other fields
        existingMilkDetail.setProduct(product);
        existingMilkDetail.setMilkTaste(milktaste);
        existingMilkDetail.setPackagingunit(packagingunit);
        existingMilkDetail.setUsageCapacity(usagecapacity);
        existingMilkDetail.setDescription(milkdetail.getDescription());
        existingMilkDetail.setImgUrl(milkdetail.getImgUrl());
        existingMilkDetail.setShelflifeofmilk(milkdetail.getShelflifeofmilk());
        existingMilkDetail.setPrice(milkdetail.getPrice());
        existingMilkDetail.setStockquantity(milkdetail.getStockquantity());

        milkdetailRepository.save(existingMilkDetail);

        // Lưu thông tin mới để ghi log
        String newInfo = String.format(
                "Sản phẩm: %s, Vị sữa: %s, Đơn vị đóng gói: %s, Dung tích: %s, Mô tả: %s, Ảnh: %s, Hạn sử dụng: %s, Giá: %.2f",
                product.getProductname(),
                milktaste.getMilktastename(),
                packagingunit.getPackagingunitname(),
                usagecapacity.getUnit(),
                milkdetail.getDescription(),
                milkdetail.getImgUrl(),
                milkdetail.getShelflifeofmilk(),
                milkdetail.getPrice()
        );

        // Log the action
        Log log = new Log();
        log.setAction("Cập nhật chi tiết sữa");
        log.setDescription(String.format(
                "Chi tiết sữa đã được cập nhật. Thông tin cũ: [%s]. Thông tin mới: [%s].",
                oldInfo,
                newInfo
        ));
        logService.saveLog(username, log);

        return ResponseEntity.ok(Map.of("message", "Cập nhật thành công"));
    }

    @Override
    public String delete(String token, Long id) {
        String username = jwtUtilities.extractUsername(token);
        Milkdetail milkdetailnew = milkdetailRepository.findById(id).get();
        if (milkdetailnew.getStatus() == Status.Delete) {
            milkdetailnew.setStatus(Status.Active);
            Log log = new Log(); // Tạo log
            log.setAction("Khôi phục san pham");
            log.setDescription(String.format(milkdetailnew.getMilkdetailcode()));
            logService.saveLog(username, log);
            milkdetailRepository.save(milkdetailnew);
            return "Khôi phục thành công";
        } else {
            milkdetailnew.setStatus(Status.Delete);
            Log log = new Log(); // Tạo log
            log.setAction("Xoa san pham");
            log.setDescription(String.format(milkdetailnew.getMilkdetailcode()));
            logService.saveLog(username, log);
            milkdetailRepository.save(milkdetailnew);
            return "Xóa thành công";
        }

    }

    @Override
    public Milkdetail getById(Long id) {
        return milkdetailRepository.findById(id).get();
    }

    public MilkDetailDto getMilkDetail(Long packagingunitID, Long milktasteID, Long productID,
            Long usagecapacityID) {
        return milkdetailRepository.getMilkDetail(packagingunitID, milktasteID, productID,
                usagecapacityID);
    }

    @Override
    public Page<Milkdetail> getMilkDetailPage(Pageable pageable) {
        return milkdetailRepository.getMilkDetailPage(pageable);
    }

    @Override
    public Page<Milkdetail> getMilkDetailsearch(String productname, Pageable pageable) {
        return milkdetailRepository.getMilkDetailsearchByProductname(productname, pageable);
    }

    @Override
    public long countLowStockMilkDetails() {
        return milkdetailRepository.countLowStockMilkDetails();
    }

    @Override
    public long countMilkDetails() {
        return milkdetailRepository.countMilkDetails();
    }

    @Override
    public String updateStockQuantity(String token, Long id, int quantity) {
        String username = jwtUtilities.extractUsername(token);
        Milkdetail milkdetail = milkdetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MilkDetail Không Tồn Tại"));
        if (quantity < 0 && milkdetail.getStockquantity() + quantity < 0) {
            throw new RuntimeException("Số lượng không hợp lệ: không đủ hàng trong kho");
        }
        milkdetail.setStockquantity(milkdetail.getStockquantity() + quantity);
        milkdetail.setStatus(1);
        milkdetailRepository.save(milkdetail);
        Log log = new Log(); // Tạo log
        log.setAction("Cập nhật số lượng");
        log.setDescription(String.format(
                "Cập nhật số lượng sản phẩm: Mã: %s, Số lượng cũ: %d, Số lượng mới: %d",
                milkdetail.getMilkdetailcode(),
                milkdetail.getStockquantity() - quantity,
                milkdetail.getStockquantity()
        ));
        logService.saveLog(username, log);
        return "Cập nhật số lượng thành công";
    }

    @Override
    public List<Milkdetail> gethethang() {
        return milkdetailRepository.gethethang();
    }

    @Override
    public Map<String, Object> checkCount(Long id, int quantity) {
        // Retrieve the Milkdetail object by ID
        Milkdetail milkdetail = milkdetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết sữa không tồn tại với ID: " + id));

        // Prepare the response data
        Map<String, Object> response = new HashMap<>();
        response.put("currentStock", milkdetail.getStockquantity());

        // Check if the stock is sufficient
        if (milkdetail.getStockquantity() < quantity) {
            response.put("status", "error");
            response.put("errors", "Số lượng trong kho không đủ");
        } else {
            response.put("status", "success");
            response.put("message", "Số lượng trong kho đủ");
        }

        return response;
    }

    @Override
    public Page<Milkdetail> filterMilkdfilterMilkdetailshopetails(Long productId, String codeMilkDetail, Long milkTasteId, Long packagingUnitId, Long usageCapacityId, Long milkBrandId, Long targetUserId, Long milkTypeId, Pageable pageable) {
        return milkdetailRepository.filterMilkdetailshop(productId, codeMilkDetail, milkTasteId, packagingUnitId, usageCapacityId, milkBrandId, targetUserId, milkTypeId, pageable);
    }

}
