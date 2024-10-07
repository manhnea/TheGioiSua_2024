package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
}
