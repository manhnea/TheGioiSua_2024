package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usermanagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    private String fullname;
    private Date registrationdate;

    private String phonenumber;

    private String address;

    private String email;
    private int status;

    @ManyToOne
    @JoinColumn(name = "roleid")
    Role role;

}
