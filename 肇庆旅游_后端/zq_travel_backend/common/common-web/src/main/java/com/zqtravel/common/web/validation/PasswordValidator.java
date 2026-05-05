package com.zqtravel.common.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final Pattern LETTER_PATTERN = Pattern.compile(".*[a-zA-Z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");

    private int minLength;
    private int maxLength;
    private boolean requireLetter;
    private boolean requireDigit;

    @Override
    public void initialize(Password annotation) {
        this.minLength = annotation.minLength();
        this.maxLength = annotation.maxLength();
        this.requireLetter = annotation.requireLetter();
        this.requireDigit = annotation.requireDigit();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (value.length() < minLength || value.length() > maxLength) {
            return false;
        }
        if (requireLetter && !LETTER_PATTERN.matcher(value).matches()) {
            return false;
        }
        if (requireDigit && !DIGIT_PATTERN.matcher(value).matches()) {
            return false;
        }
        return true;
    }
}
