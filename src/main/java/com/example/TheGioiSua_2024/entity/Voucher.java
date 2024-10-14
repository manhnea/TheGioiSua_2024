package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "voucher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vouchercode;
    private Date startdate;
    private Date enddate;
    private int discountpercentage;
    private double maxamount;
    private int usagecount;
    private int status;
}
