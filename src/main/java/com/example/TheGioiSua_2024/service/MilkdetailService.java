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

import java.util.List;

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
  public String add(String token, Milkdetail milkdetail) {
    boolean exists = milkdetailRepository.existsByProductAndMilkTasteAndPackagingunitAndUsageCapacity(
        milkdetail.getProduct().getId(),
        milkdetail.getMilkTaste().getId(),
        milkdetail.getPackagingunit().getId(),
        milkdetail.getUsageCapacity().getId()
    );

    if (exists) {
      return "Chi tiết sữa với các thông tin này đã tồn tại";
    }

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
    String username = jwtUtilities.extractUsername(token);
    try {
      Milkdetail milkdetailnew = milkdetailRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("MilkDetail Không Tồn Tại"));
      Product product = productRepository.findById(milkdetail.getProduct().getId())
          .orElseThrow(() -> new RuntimeException("Sản Phẩm Không Tồn Tại"));
      Milktaste milktaste = milktasteRepository.findById(milkdetail.getMilkTaste().getId())
          .orElseThrow(() -> new RuntimeException("Vị Sữa Không Tồn Tại"));
      Packagingunit packagingunit = packagingunitRepository.findById(
              milkdetail.getPackagingunit().getId())
          .orElseThrow(() -> new RuntimeException("Đơn Vị Đóng Gói Không Tồn Tại"));
      Usagecapacity usagecapacity = usagecapacityRepository.findById(
              milkdetail.getUsageCapacity().getId())
          .orElseThrow(() -> new RuntimeException("Dung Tích Sử Dụng Không Tồn Tại"));

      milkdetailnew.setProduct(product);
      milkdetailnew.setMilkTaste(milktaste);
      milkdetailnew.setPackagingunit(packagingunit);
      milkdetailnew.setUsageCapacity(usagecapacity);
      milkdetailnew.setPrice(milkdetail.getPrice());
      milkdetailnew.setShelflifeofmilk(milkdetail.getShelflifeofmilk());
      milkdetailnew.setDescription(milkdetail.getDescription());
      milkdetailnew.setStockquantity(milkdetail.getStockquantity());
      milkdetailnew.setImgUrl(milkdetail.getImgUrl());
      milkdetailnew.setStatus(Status.Active);
      Log log = new Log(); // Tạo log
      log.setAction("Sửa san pham");
      log.setDescription(String.format(
          "Cập nhật chi tiết sữa: Mã cũ: %s, Giá cũ: %.2f, Hạn sử dụng cũ: %s, Mô tả cũ: %s, Số lượng tồn cũ: %d, Ảnh cũ: %s, Trạng thái cũ: %d."
              + "Mã mới: %s, Giá mới: %.2f, Hạn sử dụng mới: %s, Mô tả mới: %s, Số lượng tồn mới: %d, Ảnh mới: %s, Trạng thái mới: %d. "
          ,
          milkdetailnew.getMilkdetailcode(),
          milkdetailnew.getPrice(),
          milkdetailnew.getShelflifeofmilk(),
          milkdetailnew.getDescription(),
          milkdetailnew.getStockquantity(),
          milkdetailnew.getImgUrl(),
          milkdetailnew.getStatus(),
          milkdetail.getMilkdetailcode(),
          milkdetail.getPrice(),
          milkdetail.getShelflifeofmilk(),
          milkdetail.getDescription(),
          milkdetail.getStockquantity(),
          milkdetail.getImgUrl(),
          milkdetail.getStatus()

      ));
      logService.saveLog(username, log);
      milkdetailRepository.save(milkdetailnew);
      return "Sửa thành công";
    } catch (RuntimeException e) {
      return e.getMessage();
    }
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


}
