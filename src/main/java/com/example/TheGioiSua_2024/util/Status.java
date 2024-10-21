/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.util;

/**
 *
 * @author Hieu
 */
public class Status {

    public static int Delete = 0;
    public static int Active = 1;
    public static int New = 900; // đơn mới
    public static int Waiting = 901;// chờ lấy hàng
    public static int PickUp = 902;  // lấy hàng
    public static int Took = 903;  // đã lấy
    public static int Delivery = 904;  // Giao hang
    public static int DeliverySuccessful = 905; // Giao hàng thành công
    public static int DeliveryFailed = 906;  // Giao hàng thất bại
    public static int ReturnGoods = 907;  // trả lại hàng hóa
    public static int Refund = 908;  // Chuyển hoàn
    public static int Checked = 909; // đã đối soát
    public static int CheckedCustomer = 910; // đã đối soát khách
    public static int CODpaymenttoCustomer = 911; // COD trả cho khách
    public static int WaitingforCODpayment = 912; // chờ thanh toán COD
    public static int Complete = 913; // hoàn thành
    public static int Cancellation = 914; // Đơn hủy
    public static int LateDelivery = 915; // Giao hàng trễ
    public static int PartialDelivery = 916; // Giao hàng 1 phần
    public static int OrderError = 1000; // Giao hàng 1 phần
}
