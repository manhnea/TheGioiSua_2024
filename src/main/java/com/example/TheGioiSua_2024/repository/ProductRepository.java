package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.dto.ProductDtos;
import com.example.TheGioiSua_2024.dto.ProductlstDto;
import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import com.example.TheGioiSua_2024.entity.Product;
import com.example.TheGioiSua_2024.entity.Targetuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@EntityScan(basePackages = "com.example.TheGioiSua_2024.dto")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductCode(String productCode);

    Optional<Product> findByProductUrl(String productUrl);

    @Query("SELECT p FROM Product p WHERE p.productname = :productname")
    Optional<Product> findByProductname(String productname);

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductlstDto( "
            + "p.id, "
            + "mt.id, "
            + "mb.id, "
            + "tt.id, "
            + "mt.milkTypename, "
            + "mb.milkbrandname, "
            + "tt.targetName, "
            + "p.productUrl, "
            + "p.imgUrl, "
            + "p.status, "
            + "MIN(md.price), "
            + "MAX(md.price)) "
            + "FROM Milkdetail md "
            + "JOIN md.product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "WHERE p.status = 1 "
            + "GROUP BY p.id, mt.id, mb.id, tt.id, mt.milkTypename, mb.milkbrandname, tt.targetName, p.productUrl, p.imgUrl, p.status")
    List<ProductlstDto> getPageProduct();

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductDto( "
            + "p.id, "
            + "mt.id, "
            + "mb.id, "
            + "tt.id, "
            + "mt.milkTypename, "
            + "mb.milkbrandname, "
            + "tt.targetName, "
            + "p.productUrl, "
            + "p.imgUrl, "
            + "p.status, "
            + "MIN(b.price), "
            + "MAX(b.price)) "
            + "FROM Product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "JOIN Milkdetail b ON p.id = b.product.id "
            + "WHERE p.status = 1 AND mt.id = :id "
            + "GROUP BY p.id, mt.id, mb.id, tt.id, mt.milkTypename, mb.milkbrandname, tt.targetName, p.productUrl, p.imgUrl, p.status")
    Page<ProductDto> getPageProductByTypeMilk(Pageable pageable, Long id);

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductDto( "
            + "p.id, "
            + "mt.id, "
            + "mb.id, "
            + "tt.id, "
            + "mt.milkTypename, "
            + "mb.milkbrandname, "
            + "tt.targetName, "
            + "p.productUrl, "
            + "p.imgUrl, "
            + "p.status, "
            + "MIN(b.price), "
            + "MAX(b.price)) "
            + "FROM Product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "JOIN Milkdetail b ON p.id = b.product.id "
            + "WHERE p.status = 1 AND mb.id = :id "
            + "GROUP BY p.id, mt.id, mb.id, tt.id, mt.milkTypename, mb.milkbrandname, tt.targetName, p.productUrl, p.imgUrl, p.status")
    Page<ProductDto> getPageProductByBrandMilk(Pageable pageable, Long id);

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductDto( "
            + "p.id, "
            + "mt.id, "
            + "mb.id, "
            + "tt.id, "
            + "mt.milkTypename, "
            + "mb.milkbrandname, "
            + "tt.targetName, "
            + "p.productUrl, "
            + "p.imgUrl, "
            + "p.status, "
            + "MIN(b.price), "
            + "MAX(b.price)) "
            + "FROM Product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "JOIN Milkdetail b ON p.id = b.product.id "
            + "WHERE p.status = 1 AND tt.id = :id "
            + "GROUP BY p.id, mt.id, mb.id, tt.id, mt.milkTypename, mb.milkbrandname, tt.targetName, p.productUrl, p.imgUrl, p.status")
    Page<ProductDto> getPageProductByTargetUser(Pageable pageable, Long id);

    @Query("SELECT COALESCE(MAX(p.id), 0) FROM Product p")
    Integer findMaxId();

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductDto( "
            + "p.id, "
            + "mt.id, "
            + "mb.id, "
            + "tt.id, "
            + "mt.milkTypename, "
            + "mb.milkbrandname, "
            + "tt.targetName, "
            + "p.productUrl, "
            + "p.imgUrl, "
            + "p.status,"
            + "MIN(b.price),"
            + "      MAX(b.price))"
            + "FROM Product p "
            + "JOIN p.milkBrand mb "
            + "JOIN Milkdetail b ON p.id = b.product.id "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "WHERE p.status = 1 "
            + "AND (mt.milkTypename LIKE %:searchTerm% "
            + "OR mb.milkbrandname LIKE %:searchTerm% "
            + "OR tt.targetName LIKE %:searchTerm%)")
    Page<ProductDto> getPageProductWithSearch(String searchTerm, Pageable pageable);

    @Query("SELECT p FROM Product p order by p.id desc")
    Page<Product> getProductPage(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.productname LIKE %:productname% order by p.id desc")
    Page<Product> findByProductnameContaining(String productname, Pageable pageable);

    boolean existsByProductname(String productname);

    @Query("SELECT p FROM Product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tu "
            + "WHERE (:productname IS NULL OR p.productname LIKE CONCAT('%', :productname, '%')) "
            + "AND(:milkBrand IS NULL OR mb.id = :milkBrand) "
            + "AND (:targetUser IS NULL OR tu.id = :targetUser) "
            + "AND (:milkType IS NULL OR mt.id = :milkType)")
    Page<Product> filterProducts(
            @Param("productname") String productname,
            @Param("milkBrand") Long milkBrand,
            @Param("targetUser") Long targetUser,
            @Param("milkType") Long milkType,
            Pageable pageable
    );

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductlstDto( "
            + "p.id, "
            + "mt.id, "
            + "mb.id, "
            + "tt.id, "
            + "mt.milkTypename, "
            + "mb.milkbrandname, "
            + "tt.targetName, "
            + "p.productUrl, "
            + "p.imgUrl, "
            + "p.status, "
            + "MIN(md.price), "
            + "MAX(md.price)) "
            + "FROM Milkdetail md "
            + "JOIN md.product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "WHERE p.status = 1 "
            + "GROUP BY p.id, mt.id, mb.id, tt.id, mt.milkTypename, mb.milkbrandname, tt.targetName, p.productUrl, p.imgUrl, p.status"
            + " ORDER BY p.id DESC")
    Page<ProductlstDto> getNewProduct(Pageable pageable);

    @Query("SELECT new com.example.TheGioiSua_2024.dto.ProductDtos( "
            + "p.id, "
            + "p.milkType.id, "
            + "p.milkBrand.id, "
            + "p.targetUser.id, "
            + "p.productCode, "
            + "p.productname, "
            + "p.productUrl, "
            + "p.imgUrl, "
            + "p.status,"
            + "MIN(b.price), "
            + " MAX(b.price)) "
            + "FROM Product p "
            + "JOIN Milkdetail b ON p.id = b.product.id "
            + "JOIN Invoicedetail a ON a.milkDetail.id = b.id "
            + "JOIN Invoice c ON a.invoice.id = c.id "
            + "WHERE c.status = 913 "
            + "GROUP BY p.id, p.productCode, p.productname, p.milkType.id, p.milkBrand.id, p.targetUser.id, p.productUrl, p.imgUrl, p.status "
            + "ORDER BY COUNT(a.id) DESC")
    Page<ProductDtos> getBestSeller(Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.productname = :productname AND p.milkType.id = :milkType AND p.milkBrand.id = :milkBrand AND p.targetUser.id = :targetUser")
    Product existsByProductnameAndIdbrandAndIdMilkTypeAndIdTagetUser(
            String productname,
            Long milkType,
            Long milkBrand,
            Long targetUser
    );
}
