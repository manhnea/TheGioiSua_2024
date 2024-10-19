package com.example.TheGioiSua_2024.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&-]+(.[a-zA-Z0-9_+&-]+)@[a-zA-Z0-9-]+(.[a-zA-Z0-9-]+)$";
    private static final String FULL_NAME_REGEX = "^[A-Za-zÀ-ỹà-ỹ ]+$";
    private static final String PHONE_NUMBER_REGEX = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    private static final String USERNAME_REGEX = "^[A-Za-z0-9]+$";  // Username can contain letters and numbers only

    private static final Pattern patternEmail = Pattern.compile(EMAIL_REGEX);
    private static final Pattern patternFullName = Pattern.compile(FULL_NAME_REGEX);
    private static final Pattern patternUsername = Pattern.compile(USERNAME_REGEX);

    public static boolean isValidFullName(String fullName) {
        if (fullName == null) {
            return false;
        }
        Matcher matcher = patternFullName.matcher(fullName);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.contains(" ")) {  // Check if email is null or contains spaces
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
}
