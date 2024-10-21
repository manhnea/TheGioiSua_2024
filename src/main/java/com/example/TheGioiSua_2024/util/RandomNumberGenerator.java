/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Hieu
 */
public class RandomNumberGenerator {
    public static int generateRandom4Digits() {
        // Sinh số ngẫu nhiên trong khoảng từ 1000 đến 9999
        return ThreadLocalRandom.current().nextInt(0000, 10000);
    }

    public static void main(String[] args) {
        int randomNumber = generateRandom4Digits();
        System.out.println("Số ngẫu nhiên có 4 chữ số: " + randomNumber);
    }
}
