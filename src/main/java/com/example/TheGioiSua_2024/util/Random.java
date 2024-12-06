/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.util;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Hieu
 */
public class Random {
    public static int generateRandom4Digits() {
        // Sinh số ngẫu nhiên trong khoảng từ 1000 đến 9999
        return ThreadLocalRandom.current().nextInt(0000, 10000);
    }
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final int PASSWORD_LENGTH = 12; // Độ dài mật khẩu

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        // Đảm bảo có ít nhất một ký tự từ mỗi loại
        password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Tạo các ký tự ngẫu nhiên còn lại
        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        // Trộn mật khẩu
        return shuffleString(password.toString());
    }

    private static String shuffleString(String password) {
        char[] array = password.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            // Hoán đổi
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new String(array);
    }
    public static void main(String[] args) {
        int randomNumber = generateRandom4Digits();
        System.out.println("Số ngẫu nhiên có 4 chữ số: " + randomNumber);
    }
}
