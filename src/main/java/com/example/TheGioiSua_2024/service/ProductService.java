package com.example.TheGioiSua_2024.service;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.repository.MilkbrandRepository;
import com.example.TheGioiSua_2024.repository.MilktypeRepository;
import com.example.TheGioiSua_2024.repository.ProductRepository;
import com.example.TheGioiSua_2024.repository.TargetuserRepository;
import com.example.TheGioiSua_2024.service.impl.IProductService;
import com.example.TheGioiSua_2024.util.RandomNumberGenerator;
import com.example.TheGioiSua_2024.util.Status;
import com.example.TheGioiSua_2024.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MilktypeRepository milktypeRepository;
    @Autowired
    private MilkbrandRepository milkbrandRepository;
    @Autowired
    private TargetuserRepository targetuserRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public String addProduct(Product product) {
        try {
            Integer maxId = productRepository.findMaxId();

            if (maxId == null) {
                maxId = 1;  // Nếu bảng trống thì bắt đầu từ 1
            } else {
                maxId++;
            }

            // Tạo mã chi tiết sản phẩm theo định dạng "MD" + 3 số
            String productCode = String.format("SP%03d", maxId);
            if (product.getMilkBrand().getId() == null) {
                return "Thương Hiệu Không Được Để Trống.";
            }
            if (product.getMilkType().getId() == null) {
                return "Loại Sữa Không Được Để Trống.";
            }
            if (product.getTargetUser().getId() == null) {
                return "Đối Tượng Sử Dụng Không Được Để Trống.";
            }
            String productname = product.getProductname().trim();
            product.setProductCode(productCode);
            product.setProductname(productname);
            MilkType milkType = milktypeRepository.findById(product.getMilkType().getId()).orElseThrow(() -> new RuntimeException("Loại Sữa Không Tồn Tại"));
            Milkbrand milkbrand = milkbrandRepository.findById(product.getMilkBrand().getId()).orElseThrow(() -> new RuntimeException("Hãng Sữa Không Tồn Tại"));
            String nameMilkBrand = milkbrand.getMilkbrandname();
            String nameMilkType = milkType.getMilkTypename();
            String urlProduct = StringUtil.replaceSpacesWithUnderscore(nameMilkType) + "_" + StringUtil.replaceSpacesWithUnderscore(nameMilkBrand) + "_" + RandomNumberGenerator.generateRandom4Digits();

            if (productRepository.findByProductname(productname).isPresent()) {
                return "Sản phẩm với tên này đã tồn tại.";
            }
            if (productRepository.findByProductCode(productCode).isPresent()) {
                return "Sản phẩm với mã này đã tồn tại.";
            }
            while (productRepository.findByProductUrl(StringUtil.removeAccent(urlProduct)).isPresent()) {
                urlProduct = StringUtil.replaceSpacesWithUnderscore(nameMilkType) + "_" + StringUtil.replaceSpacesWithUnderscore(nameMilkBrand) + "_" + RandomNumberGenerator.generateRandom4Digits();
            }
            product.setProductUrl(StringUtil.removeAccent(urlProduct));
            product.setStatus(Status.Active);
            productRepository.save(product);
            return "Thêm sản phẩm thành công.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String updateProduct(Long id, Product product) {
        try {
            if (product.getMilkBrand().getId() == null) {
                return "Thương Hiệu Không Được Để Trống.";
            }
            if (product.getMilkType().getId() == null) {
                return "Loại Sữa Không Được Để Trống.";
            }
            if (product.getTargetUser().getId() == null) {
                return "Đối Tượng Sử Dụng Không Được Để Trống.";
            }
            // Kiểm tra xem sản phẩm hiện tại có tồn tại hay không
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sản Phẩm Không Tồn Tại"));

            // Kiểm tra các thuộc tính liên quan
            MilkType milkType = milktypeRepository.findById(existingProduct.getMilkType().getId())
                    .orElseThrow(() -> new RuntimeException("Loại sữa không tồn tại"));
            Milkbrand milkbrand = milkbrandRepository.findById(existingProduct.getMilkBrand().getId())
                    .orElseThrow(() -> new RuntimeException("Thương hiệu sữa không tồn tại"));
            Targetuser targetuser = targetuserRepository.findById(existingProduct.getTargetUser().getId())
                    .orElseThrow(() -> new RuntimeException("Người dùng mục tiêu không tồn tại"));

            // Kiểm tra xem tên hoặc mã sản phẩm có trùng không
            if (!existingProduct.getProductname().equals(product.getProductname())
                    && productRepository.findByProductname(product.getProductname()).isPresent()) {
                return "Sản phẩm với tên này đã tồn tại.";
            }

            // Cập nhật thông tin sản phẩm
            existingProduct.setMilkType(milkType);
            existingProduct.setMilkBrand(milkbrand);
            existingProduct.setTargetUser(targetuser);
            existingProduct.setStatus(Status.Active);  // Assuming Status.Active is 1

            productRepository.save(existingProduct);

            return "Cập nhật sản phẩm thành công.";

        } catch (RuntimeException e) {
            // Trả về thông báo lỗi cụ thể
            return e.getMessage();
        }
    }

    @Override
    public String deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id).orElseThrow();
        if (existingProduct.getStatus() == Status.Delete) {
            existingProduct.setStatus(Status.Active);
            productRepository.save(existingProduct);
            return "Khôi phục sản phẩm thành công.";
        } else {
            existingProduct.setStatus(Status.Delete);
            productRepository.save(existingProduct);
            return "Xóa sản phẩm thành công.";
        }
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<ProductDto> getPageProduct(Pageable pageable) {
        return productRepository.getPageProduct(pageable);
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
}
