/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.repository;

import com.example.TheGioiSua_2024.entity.Role;
import com.example.TheGioiSua_2024.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Query(value = "SELECT u FROM User u Join u.role r where r.id != 1 ORDER BY u.id DESC")
  Page<User> getUserPage(Pageable pageable);

  @Query(value = "SELECT u FROM User u Join u.role r where r.id = 2 ORDER BY u.id DESC")
  Page<User> getCustomerPage(Pageable pageable);

  @Query(value = "SELECT u FROM User u JOIN u.role r " +
          "WHERE r.id != 1 " +
          "AND (:username IS NULL OR u.username LIKE %:username%) " +
          "AND (:email IS NULL OR u.email LIKE %:email%) " +
          "AND (:fullname IS NULL OR u.fullname LIKE %:fullname%) " +
          "AND (:phonenumber IS NULL OR u.phonenumber LIKE %:phonenumber%) " +
          "AND (:address IS NULL OR u.address LIKE %:address%) " +
          "ORDER BY u.id DESC")
  Page<User> getUserPages(Pageable pageable,
                          @Param("username") String username,
                          @Param("email") String email,
                          @Param("fullname") String fullname,
                          @Param("phonenumber") String phonenumber,
                          @Param("address") String address);



  @Query(value = "SELECT u FROM User u JOIN u.role r " +
          "WHERE r.id = 2 " +
          "AND (:username IS NULL OR u.username LIKE %:username%) " +
          "AND (:email IS NULL OR u.email LIKE %:email%) " +
          "AND (:fullname IS NULL OR u.fullname LIKE %:fullname%) " +
          "AND (:phonenumber IS NULL OR u.phonenumber LIKE %:phonenumber%) " +
          "AND (:address IS NULL OR u.address LIKE %:address%) " +
          "ORDER BY u.id DESC")
  Page<User> getCustomerPageseachr(Pageable pageable,
                          @Param("username") String username,
                          @Param("email") String email,
                          @Param("fullname") String fullname,
                          @Param("phonenumber") String phonenumber,
                          @Param("address") String address);

}

