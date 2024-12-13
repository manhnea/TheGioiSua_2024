package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.ProductDto;
import com.example.TheGioiSua_2024.dto.ProductlstDto;
import com.example.TheGioiSua_2024.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
  Page<ProductlstDto> getPageProduct(Pageable pageable);


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
    + "p.status) "
    + "FROM Product p "
    + "JOIN p.milkBrand mb "
    + "JOIN p.milkType mt "
    + "JOIN p.targetUser tt "
    + "WHERE p.status = 1 AND mt.id =:id")
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
    + "p.status) "
    + "FROM Product p "
    + "JOIN p.milkBrand mb "
    + "JOIN p.milkType mt "
    + "JOIN p.targetUser tt "
    + "WHERE p.status = 1 AND mb.id =:id")
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
    + "p.status) "
    + "FROM Product p "
    + "JOIN p.milkBrand mb "
    + "JOIN p.milkType mt "
    + "JOIN p.targetUser tt "
    + "WHERE p.status = 1 AND tt.id =:id")
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
    + "p.status) "
    + "FROM Product p "
    + "JOIN p.milkBrand mb "
    + "JOIN p.milkType mt "
    + "JOIN p.targetUser tt "
    + "WHERE p.status = 1 "
    + "AND (mt.milkTypename LIKE %:searchTerm% "
    + "OR mb.milkbrandname LIKE %:searchTerm% "
    + "OR tt.targetName LIKE %:searchTerm%)")
  Page<ProductDto> getPageProductWithSearch(String searchTerm, Pageable pageable);

  @Query("SELECT p FROM Product p ")
  Page<Product> getProductPage(Pageable pageable);
}
