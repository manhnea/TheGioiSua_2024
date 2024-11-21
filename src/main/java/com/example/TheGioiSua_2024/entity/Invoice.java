package com.example.TheGioiSua_2024.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invoice")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoicecode;
    private String deliveryaddress;
    private String paymentmethod;
    private String phonenumber;
    @CreationTimestamp
    private LocalDateTime creationdate;
    @Min(value = 0, message = "Số tiền giảm giá phải là số không âm")
    private int discountamount;
    @Min(value = 0, message = "Tổng số tiền phải là số không âm")
    private int totalamount;
    private int status;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonIgnore  // Tránh tuần tự hóa đối tượng userInvoices
    private Set<Userinvoice> userInvoices;

    @ManyToOne
    @JoinColumn(name = "voucherid")
    private Voucher voucher;

    @Override
    public String toString() {
        return "Invoice{"
                + "id=" + id
                + ", invoicecode='" + invoicecode + '\''
                + ", deliveryaddress='" + deliveryaddress + '\''
                + ", paymentmethod='" + paymentmethod + '\''
                + ", phonenumber='" + phonenumber + '\''
                + ", creationdate=" + creationdate
                + ", discountamount=" + discountamount
                + ", totalamount=" + totalamount
                + ", status=" + status
                + ", voucher=" + (voucher != null ? voucher.getId() : null)
                + // Tránh in toàn bộ đối tượng Voucher
                '}';
    }

}
