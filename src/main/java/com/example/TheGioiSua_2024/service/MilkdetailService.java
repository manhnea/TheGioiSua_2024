package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.*;
import com.example.TheGioiSua_2024.repository.*;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IMilkdetailService;
import com.example.TheGioiSua_2024.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    Optional<Milkdetail> existingmilkdetail = milkdetailRepository.findByIds(getProduct, getMilkTaste, getPackagingunit, getUsageCapacity);
    if (existingmilkdetail.isPresent()) {
        return "Chi tiết sữa này đã tồn tại."; // Milk detail already exists
    }
    return null; // No duplicates found
  }

  @Override
  public String add(String token, Milkdetail milkdetail) {


    // Check if all related entities exist
    boolean allEntitiesExist = productRepository.existsById(milkdetail.getProduct().getId()) &&
        milktasteRepository.existsById(milkdetail.getMilkTaste().getId()) &&
        packagingunitRepository.existsById(milkdetail.getPackagingunit().getId()) &&
        usagecapacityRepository.existsById(milkdetail.getUsageCapacity().getId());

    if (!allEntitiesExist) {
      return "Một trong các thực thể liên quan (Sản Phẩm, Vị Sữa, Đơn Vị Đóng Gói, Dung Tích Sử Dụng) không tồn tại";
    }
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
    String username = jwtUtilities.extractUsername(token);

    String message = String.format(
        "Tên sản phẩm: %s, Mô tả: %s, Đơn vị đóng gói: %s, Hương vị: %s, Số lượng trong kho: %d, URL hình ảnh: %s, Dung tích sử dụng: %s, Hạn sử dụng: %s, Trạng thái: %s, Mã chi tiết sữa: %s, Giá: %.2f",
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

    Log log = new Log(); // Tạo log
    log.setAction("Thêm voucher");
    log.setDescription(message);
    logService.saveLog(username, log);
    milkdetailRepository.save(milkdetail);
    return "Thêm thành công";
  }

  @Override
  public String update(String token, Long id, Milkdetail milkdetail) {
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

    Packagingunit packagingunit = packagingunitRepository.findById(
                    milkdetail.getPackagingunit().getId())
            .orElseThrow(() -> new RuntimeException(
                    "Đơn vị đóng gói không tồn tại với ID: " + milkdetail.getPackagingunit().getId()));

    Usagecapacity usagecapacity = usagecapacityRepository.findById(
                    milkdetail.getUsageCapacity().getId())
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

    // Update the status based on stock quantity
    if (milkdetail.getStockquantity() <= 0) {
      existingMilkDetail.setStatus(0); // Stock quantity is 0 or less, set status to 0
    } else {
      existingMilkDetail.setStatus(1); // Stock quantity is greater than 0, set status to 1
    }

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

    // Ghi log
    Log log = new Log();
    log.setAction("Cập nhật chi tiết sữa");
    log.setDescription(String.format(
            "Chi tiết sữa đã được cập nhật. Thông tin cũ: [%s]. Thông tin mới: [%s].",
            oldInfo,
            newInfo
    ));
    logService.saveLog(username, log);

    return "Cập nhật thành công";
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



}
