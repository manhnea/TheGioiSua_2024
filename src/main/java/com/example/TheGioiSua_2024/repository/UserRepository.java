/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Hieu
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Optional<User> findByUsername(String username);

  User findByEmail(String email);


  @Query(value = "SELECT u.username\n"
      + "FROM user u\n"
      + "WHERE u.roleid = 2 AND u.status = 1\n"
      + "ORDER BY u.registrationdate DESC\n"
      + "LIMIT 5;", nativeQuery = true)
  List<String> findAllUsernames();

  @Query("SELECT COUNT(*) FROM User u WHERE u.status = 1 AND u.role.id = 2")
  long countUsersByStatusAndRole();


}


