package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MilkdetailRepository extends JpaRepository<Milkdetail, Long> {

  Optional<Milkdetail> existsBymilkdetailcode(String milkdetailcode);

  //    @Query("SELECT new com.example.TheGioiSua_2024.dto.MilkDetailDto(\n"
//            + " md.id,\n"
//            + " md.price, \n"
//            + " md.stockquantity, \n"
//            + " md.imgUrl,\n"
//            + " md.status) \n"
//            + "FROM Milkdetail md\n"
//            + "JOIN md.product p \n" // Tham chiếu đến quan hệ trong entity Milkdetail
//            + "JOIN p.milkBrand mb \n" // Tham chiếu đến quan hệ trong entity Product
//            + "JOIN p.milkType mt \n"
//            + "JOIN p.targetUser tt \n"
//            + "JOIN md.usageCapacity uc \n" // Tham chiếu đến quan hệ trong entity Milkdetail
//            + "JOIN md.packagingunit pu \n"
//            + "JOIN md.milkTaste mtt \n"
//            + "WHERE mt.id = :milktypeID \n"
//            + "AND mb.id = :milkBrandID \n"
//            + "AND pu.id = :packagingunitID \n"
//            + "AND mtt.id = :milktasteID \n"
//            + "AND p.id = :productID \n"
//            + "AND tt.id = :targetuserID \n"
//            + "AND uc.id = :usagecapacityID \n"
//            + "AND md.status = 1")
//    Page<MilkDetailDto> getPageMilkDetail(Pageable pageable,
//            @Param("milktypeID") Long milktypeID,
//            @Param("milkBrandID") Long milkBrandID,
//            @Param("packagingunitID") Long packagingunitID,
//            @Param("milktasteID") Long milktasteID,
//            @Param("productID") Long productID,
//            @Param("targetuserID") Long targetuserID,
//            @Param("usagecapacityID") Long usagecapacityID);
  @Query("SELECT new com.example.TheGioiSua_2024.dto.MilkDetailDto("
      + " md.id,"
      + " pu.packagingunitname,"
      + " mt.milkTypename," // chú ý tên chính xác của thuộc tính
      + " mb.milkbrandname,"
      + " mtt.milktastename,"
      + " uc.capacity,"
      + " uc.unit,"
      + " tt.targetName,"
      + " md.price,"
      + " md.stockquantity,"
      + " md.imgUrl,"
      + " md.shelflifeofmilk,"
      + " md.status) "
      + "FROM Milkdetail md "
      + "JOIN md.product p "
      + "JOIN p.milkBrand mb "
      + "JOIN p.milkType mt "
      + "JOIN p.targetUser tt "
      + "JOIN md.usageCapacity uc "
      + "JOIN md.packagingunit pu "
      + "JOIN md.milkTaste mtt "
      + "WHERE pu.id = :packagingunitID "
      + "AND mtt.id = :milktasteID "
      + "AND p.id = :productID "
      + "AND uc.id = :usagecapacityID " // Thêm dấu cách trước AND
      + "AND md.status = 1")
  MilkDetailDto getMilkDetail(
      Long packagingunitID,
      Long milktasteID,
      Long productID,
      Long usagecapacityID
  );

  @Query("SELECT COALESCE(MAX(m.id), 0) FROM Milkdetail m")
  Integer findMaxId();

  @Query("SELECT COUNT(m) > 0 FROM Milkdetail m WHERE m.product.id = :id AND m.milkTaste.id = :id1 AND m.packagingunit.id = :id2 AND m.usageCapacity.id = :id3")
  boolean existsByProductAndMilkTasteAndPackagingunitAndUsageCapacity(Long id, Long id1, Long id2,
      Long id3);

  @Query("SELECT a FROM Milkdetail a")
  Page<Milkdetail> getMilkDetailPage(Pageable pageable);

  @Query("SELECT md FROM Milkdetail md JOIN md.product p WHERE p.productname LIKE %:productname%")
  Page<Milkdetail> getMilkDetailsearchByProductname(@Param("productname") String productname,
      Pageable pageable);

  @Query("SELECT COUNT(a) FROM Milkdetail a WHERE a.stockquantity < 10")
  long countLowStockMilkDetails();

  @Query("SELECT COUNT(a) FROM Milkdetail a")
  long countMilkDetails();


}
