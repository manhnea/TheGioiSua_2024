package com.example.TheGioiSua_2024.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class UserValidator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    private static final String FULL_NAME_REGEX = "^[A-Za-zÀ-ỹà-ỹ ]+$";  // Full name with spaces and accented characters
    private static final String PHONE_NUMBER_REGEX = "(84|0[3|5|7|8|9])+([0-9]{8})\\b"; // Vietnamese phone number pattern
    private static final String USERNAME_REGEX = "^[A-Za-z0-9]{3,20}$"; // Username (3 to 20 alphanumeric characters)
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; // Strong password pattern

    private static final Pattern patternEmail = Pattern.compile(EMAIL_REGEX);
    private static final Pattern patternFullName = Pattern.compile(FULL_NAME_REGEX);
    private static final Pattern patternUsername = Pattern.compile(USERNAME_REGEX);
    private static final Pattern patternPhoneNumber = Pattern.compile(PHONE_NUMBER_REGEX);
    private static final Pattern patternPassword = Pattern.compile(PASSWORD_REGEX);

    public static boolean isValidFullName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return false;
        }
        Matcher matcher = patternFullName.matcher(fullName);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.contains(" ")) { // Check if email is null or contains spaces
            return false;
        }
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.contains(" ")) {  // Check if username is null or contains spaces
            return false;
        }
        Matcher matcher = patternUsername.matcher(username);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.contains(" ")) {  // Check if password is null or contains spaces
            return false;
        }
        Matcher matcher = patternPassword.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.contains(" ")) {  // Check if phone number is null or contains spaces
            return false;
        }
        Matcher matcher = patternPhoneNumber.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isValidRegistrationDate(Date registrationDate) {
        if (registrationDate == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        return registrationDate.getTime() <= currentTime; // Registration date should not be in the future
    }

    public static boolean isValidAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }
        return address.length() >= 5 && address.length() <= 255; // Address length should be between 5 and 255 characters
    }
}
