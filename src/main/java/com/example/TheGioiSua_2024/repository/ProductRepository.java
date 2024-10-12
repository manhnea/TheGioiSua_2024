package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.boot.autoconfigure.domain.EntityScan;

//@EnableJpaRepositories(basePackages = "com.yourpackage.repository")
@EntityScan(basePackages = "com.example.TheGioiSua_2024.dto")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductDto("
            + "md.id, "
            + "p.productCode, "
            + "pu.packagingunitname, "
            + "c.containername, "
            + "mt.milkTypename, "
            + "mb.milkbrandname, "
            + "mtt.milktastename, "
            + "s.sizename, "
            + "uc.capacity, "
            + "uc.unit, "
            + "tt.targetName, "
            + "md.price, "
            + "md.stockquantity, "
            + "md.description, "
            + "p.status) "
            + "FROM Milkdetail md "
            + "JOIN md.product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "JOIN md.size s "
            + "JOIN md.usageCapacity uc "
            + "JOIN md.packagingUnit pu "
            + "JOIN md.milkTaste mtt "
            + "JOIN md.container c")
    Page<ProductDto> getPage(Pageable pageable);

}
