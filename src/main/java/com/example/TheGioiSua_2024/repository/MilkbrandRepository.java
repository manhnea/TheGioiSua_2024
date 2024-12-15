package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MilkbrandRepository extends JpaRepository<Milkbrand, Long> {
    Optional<Milkbrand> findByMilkbrandname(String milkbrandname);

    @Query("SELECT m FROM Milkbrand m WHERE m.id = ?1")
    Milkbrand findBydadata(Long id);

    @Query("SELECT m FROM Milkbrand m " +
            "WHERE (:milkbrandname IS NULL OR :milkbrandname = '' OR LOWER(m.milkbrandname) LIKE LOWER(CONCAT('%', :milkbrandname, '%'))) " +
            "AND (:status IS NULL OR CAST(m.status AS string) = :status)")
    Page<Milkbrand> findByMilkbrandnamePage(@Param("milkbrandname") String milkbrandname,
                                            @Param("status") String status,
                                            Pageable pageable);
    boolean existsByMilkbrandname(String milkbrandname);
    @Query(value = "SELECT m FROM Milkbrand m order by m.id desc")
    Page<Milkbrand> getMilkbrandPage(Pageable pageable);
}
