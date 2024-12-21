package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.Validator.MilkTasteValidator;
import com.example.TheGioiSua_2024.Validator.ProductValidator;
import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.dto.ProductDtos;
import com.example.TheGioiSua_2024.dto.ProductlstDto;
import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.repository.MilkbrandRepository;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.repository.ProductRepository;
import com.example.TheGioiSua_2024.repository.TargetuserRepository;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.impl.IProductService;
import com.example.TheGioiSua_2024.util.Random;
import com.example.TheGioiSua_2024.util.Status;
import com.example.TheGioiSua_2024.util.StringUtil;

import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService implements IProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MilktypeRepository milktypeRepository;
    @Autowired
    private MilkbrandRepository milkbrandRepository;
    @Autowired
    private TargetuserRepository targetuserRepository;
    @Autowired
    private JwtUtilities jwtUtilities;
    @Autowired
    private logService logService;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<?> addProduct(String token, Product product) {
        String error = ProductValidator.validateProduct(product);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }

        Integer maxId = productRepository.findMaxId();

        if (maxId == null) {
            maxId = 1;  // Nếu bảng trống thì bắt đầu từ 1
        } else {
            maxId++;
        }

        // Tạo mã chi tiết sản phẩm theo định dạng "MD" + 3 số
        String productCode = String.format("SP%03d", maxId);

        String productname = product.getProductname().trim();
        product.setProductCode(productCode);
        product.setProductname(productname);
        MilkType milkType = milktypeRepository.findById(product.getMilkType().getId())
                .orElseThrow(() -> new RuntimeException("Loại Sữa Không Tồn Tại"));
        Milkbrand milkbrand = milkbrandRepository.findById(product.getMilkBrand().getId())
                .orElseThrow(() -> new RuntimeException("Hãng Sữa Không Tồn Tại"));
        String nameMilkBrand = milkbrand.getMilkbrandname();
        String nameMilkType = milkType.getMilkTypename();
        String urlProduct = StringUtil.replaceSpacesWithUnderscore(nameMilkType) + "_"
                + StringUtil.replaceSpacesWithUnderscore(nameMilkBrand) + "_"
                + Random.generateRandom4Digits();
        while (productRepository.findByProductUrl(StringUtil.removeAccent(urlProduct)).isPresent()) {
            urlProduct = StringUtil.replaceSpacesWithUnderscore(nameMilkType) + "_"
                    + StringUtil.replaceSpacesWithUnderscore(nameMilkBrand) + "_"
                    + Random.generateRandom4Digits();
        }
        product.setProductUrl(StringUtil.removeAccent(urlProduct));
        product.setStatus(Status.Active);
        String username = jwtUtilities.extractUsername(token);
        Log log = new Log();
        log.setAction("Thêm sản phẩm");
        log.setDescription(
                String.format("Sản phẩm: %s - Loại: %s, Thương hiệu: %s, Đối tượng sử dụng: %s",
                        product.getProductname(),
                        product.getMilkType(),
                        product.getMilkBrand(),
                        product.getTargetUser()));
        logService.saveLog(username, log);
        productRepository.save(product);
        return ResponseEntity.ok(Map.of("success", "Thêm sản phẩm thành công."));

    }

    @Override
    public ResponseEntity<?> updateProduct(String token, Long id, Product product) {
        String error = ProductValidator.validateProduct(product);
        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }
        String username = jwtUtilities.extractUsername(token);
        Log log = new Log();

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản Phẩm Không Tồn Tại"));

        // Kiểm tra các thuộc tính liên quan
        MilkType milkType = milktypeRepository.findById(product.getMilkType().getId()).orElseThrow();
        String changeLog = String.format(
                "Tên sản phẩm: %s -> %s, Loại: %s -> %s, Thương hiệu: %s -> %s, Đối tượng: %s -> %s",
                existingProduct.getProductname(), product.getProductname(),
                existingProduct.getMilkType().getMilkTypename(), milkType.getMilkTypename(),
                existingProduct.getMilkBrand().getMilkbrandname(),
                milkbrandRepository.findById(product.getMilkBrand().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Milk brand not found"))
                        .getMilkbrandname(),
                existingProduct.getTargetUser().getTargetName(),
                targetuserRepository.findById(product.getTargetUser().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Target user not found"))
                        .getTargetName()
        );

        Milkbrand milkbrand = milkbrandRepository.findById(product.getMilkBrand().getId())
                .orElseThrow();
        Targetuser targetuser = targetuserRepository.findById(product.getTargetUser().getId())
                .orElseThrow();

        if (productRepository.existsByProductnameAndIdbrandAndIdMilkTypeAndIdTagetUser(product.getProductname(),milkType,milkbrand,targetuser )) {
            return ResponseEntity.badRequest().body(Map.of("error", "Tên sản phẩm đã tồn tại"));
        }
        existingProduct.setProductname(product.getProductname());
        existingProduct.setMilkType(milkType);
        existingProduct.setMilkBrand(milkbrand);
        existingProduct.setTargetUser(targetuser);
        log.setAction("Cập nhật sản phẩm");
        log.setDescription(changeLog);
        logService.saveLog(username, log);
        productRepository.save(existingProduct);
        return ResponseEntity.ok(Map.of("success", "Cập nhật sản phẩm thành công."));

    }

    @Override
    public ResponseEntity<?> deleteProduct(String token, Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow();
        String username = jwtUtilities.extractUsername(token);
        Log log = new Log();
        if (existingProduct.getStatus() == Status.Delete) {
            existingProduct.setStatus(Status.Active);
            log.setAction("Khôi phục sản phẩm");
            log.setDescription(String.format("Khôi phục sản phẩm: %s", existingProduct.getProductname()));
            logService.saveLog(username, log);
            productRepository.save(existingProduct);
            return ResponseEntity.ok(Map.of("success", "Khôi phục sản phẩm thành công."));
        } else {
            existingProduct.setStatus(Status.Delete);
            log.setAction("Xóa sản phẩm");
            log.setDescription(String.format("Xóa sản phẩm: %s", existingProduct.getProductname()));
            logService.saveLog(username, log);
            productRepository.save(existingProduct);
            return ResponseEntity.ok(Map.of("success", "Xóa sản phẩm thành công."));
        }
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public List<ProductlstDto> getPageProduct() {
        return productRepository.getPageProduct();
    }

    @Override
    public Page<ProductDto> getPageProductByTypeMilk(Pageable pageable, Long id) {
        return productRepository.getPageProductByTypeMilk(pageable, id);
    }

    @Override
    public Page<ProductDto> getPageProductByBrandMilk(Pageable pageable, Long id) {
        return productRepository.getPageProductByBrandMilk(pageable, id);
    }

    @Override
    public Page<ProductDto> getPageProductByTargetUser(Pageable pageable, Long id) {
        return productRepository.getPageProductByTargetUser(pageable, id);
    }

    @Override
    public Page<ProductDto> getPageProductWithSearch(String searchTerm, Pageable pageable) {
        return productRepository.getPageProductWithSearch(searchTerm, pageable);
    }

    @Override
    public Page<Product> getProductPage(Pageable pageable) {
        return productRepository.getProductPage(pageable);
    }

    @Override
    public Page<Product> getProductPageByTypeMilk(String productname, Pageable pageable) {
        return productRepository.findByProductnameContaining(productname, pageable);
    }

    @Override
    public Page<Product> filterProduct(String productname, Long milkBrandId, Long targetUserId, Long milkTypeId, Pageable pageable) {
        return productRepository.filterProducts(productname, milkBrandId, targetUserId, milkTypeId, pageable);
    }

    @Override
    public Page<ProductlstDto> getNewProduct(Pageable pageable) {
        return productRepository.getNewProduct(pageable);
    }

    @Override
    public Page<ProductDtos> getBestSeller(Pageable pageable) {
        return productRepository.getBestSeller(pageable);
    }

}
