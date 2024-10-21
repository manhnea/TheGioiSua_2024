package com.example.TheGioiSua_2024.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 100, message = "Tên sản phẩm không được dài quá 100 ký tự")
    @Pattern(regexp = "^[A-Za-zÀ-ỹà-ỹ0-9 ]+$", message = "Tên sản phẩm chỉ được chứa các ký tự chữ, số và khoảng trắng")
    private String productname;

    @NotBlank(message = "Mã sản phẩm là bắt buộc")
    @JsonProperty("productcode")
    @Size(min = 5, max = 20, message = "Mã sản phẩm phải có độ dài từ 5 đến 20 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Mã sản phẩm chỉ được chứa các ký tự chữ và số (A-Z, a-z, 0-9)")
    private String productCode;

    @ManyToOne
    @JoinColumn(name = "milktypeid", nullable = false)
    private MilkType milkType;

    @ManyToOne
    @JoinColumn(name = "milkbrandid", nullable = false)
    private Milkbrand milkBrand;

    @ManyToOne
    @JoinColumn(name = "targetuserid", nullable = false)
    private Targetuser targetUser;
    private String productUrl;
    @Min(value = 0, message = "Trạng thái không hợp lệ")
    @Max(value = 1, message = "Trạng thái không hợp lệ")
    private int status;
}
