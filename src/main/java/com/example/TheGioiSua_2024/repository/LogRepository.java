package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Log;
import com.example.TheGioiSua_2024.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

  @Query("SELECT l FROM Log l WHERE l.user.id = ?1")
  List<Log> findByUserId(int userId);
}
