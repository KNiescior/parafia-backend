package com.example.holyclick.user.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidatorService implements PasswordValidatorInterface {

    private final int MIN_PASSWORD_LENGTH = 8;

    @Override
    public boolean isPasswordValid(String password) {
        return isPasswordLongEnough(password) &&
                isPasswordHavingUppercaseLetter(password) &&
                isPasswordHavingDigit(password) &&
                isPasswordHavingSpecialChar(password);
    }

    private boolean isPasswordLongEnough(String password) {
        boolean value = password.length() >= MIN_PASSWORD_LENGTH;

        return value;
    }

    private boolean isPasswordHavingUppercaseLetter(String password) {
        boolean value = password.chars().anyMatch(Character::isUpperCase);

        return value;
    }

    private boolean isPasswordHavingDigit(String password) {
        boolean value = password.chars().anyMatch(Character::isDigit);

        return value;
    }

    private boolean isPasswordHavingSpecialChar(String password) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9]");
        Matcher m = p.matcher(password);
        boolean value = m.find();

        return value;
    }
}