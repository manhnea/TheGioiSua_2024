package com.example.TheGioiSua_2024.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Mã voucher không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Mã voucher chỉ được chứa các ký tự chữ và số (A-Z, a-z, 0-9), không được chứa khoảng trắng")
    @Size(min = 5, max = 20, message = "Mã voucher phải từ 5 đến 20 ký tự")
    private String vouchercode;


    @NotNull(message = "Ngày bắt đầu không được để trống")
    @FutureOrPresent(message = "Ngày bắt đầu phải là hôm nay hoặc một ngày trong tương lai")
    private Date startdate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @Future(message = "Ngày kết thúc phải là một ngày trong tương lai")
    private Date enddate;

    @Min(value = 1, message = "Phần trăm giảm giá phải lớn hơn hoặc bằng 1")
    @Max(value = 100, message = "Phần trăm giảm giá không được vượt quá 100")
    private int discountpercentage;

    @PositiveOrZero(message = "Số tiền tối đa phải lớn hơn hoặc bằng 0")
    private double maxamount;

    @Min(value = 0, message = "Số lần sử dụng phải lớn hơn hoặc bằng 0")
    private int usagecount;

    @Min(value = 0, message = "Trạng thái không hợp lệ")
    @Max(value = 1, message = "Trạng thái không hợp lệ")
    private int status;
}
