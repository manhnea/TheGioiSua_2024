/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author Hieu
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShippingstatusDto {

    private String gcode;
    private String code;
    private String order_id;
    private String weight;
    private String fee;
    private String cod;
    private String payer;
    private String status;
    private String message;
    private String trackingUrl;

    @Override
    public String toString() {
        return "ClassName {"
                + "gcode='" + gcode + '\''
                + ", code='" + code + '\''
                + ", orderId='" + order_id + '\''
                + ", weight='" + weight + '\''
                + ", fee='" + fee + '\''
                + ", cod='" + cod + '\''
                + ", payer='" + payer + '\''
                + ", status='" + status + '\''
                + ", message='" + message + '\''
                + ", trackingUrl='" + trackingUrl + '\''
                + '}';
    }

}
