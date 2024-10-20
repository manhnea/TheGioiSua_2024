package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.dto.MilkDetailDto;
import com.example.TheGioiSua_2024.entity.Milkdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface MilkdetailRepository extends JpaRepository<Milkdetail, Long> {

    Optional<Milkdetail> existsBymilkdetailcode(String milkdetailcode);

    @Query("SELECT new com.example.TheGioiSua_2024.dto.MilkDetailDto(md.id, p.productCode, md.milkdetailcode, "
            + "pu.packagingunitname, mt.milkTypename, mb.milkbrandname, mtt.milktastename, "
            + "uc.capacity, uc.unit, tt.targetName, md.price, md.stockquantity, md.description, p.status) "
            + "FROM Milkdetail md "
            + "JOIN md.product p "
            + "JOIN p.milkBrand mb "
            + "JOIN p.milkType mt "
            + "JOIN p.targetUser tt "
            + "JOIN md.usageCapacity uc "
            + "JOIN md.packagingunit pu "
            + "JOIN md.milkTaste mtt")
    Page<MilkDetailDto> getPageMilkDetail(Pageable pageable);

}
