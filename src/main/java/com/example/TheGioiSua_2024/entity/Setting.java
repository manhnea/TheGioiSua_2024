package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logo;
    private String nameshop;
    private String apikey;
    private String valuebank;
    private String stk;
    private String hotline;
    private String fullname;
    private String email;
    private String address;
}
