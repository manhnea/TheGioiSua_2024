package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {


  Page<Log> findByUserId(int userId, Pageable pageable);

  @Query("SELECT l FROM Log l WHERE l.user.username = :username")
  Page<Log> findByUsername(@Param("username") String username, Pageable pageable);
}
