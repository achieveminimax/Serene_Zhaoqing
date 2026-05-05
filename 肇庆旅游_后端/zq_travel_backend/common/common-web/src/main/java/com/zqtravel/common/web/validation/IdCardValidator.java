package com.zqtravel.common.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class IdCardValidator implements ConstraintValidator<IdCard, String> {

    private static final Pattern ID_CARD_18 = Pattern.compile("^[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[\\dXx]$");
    private static final Pattern ID_CARD_15 = Pattern.compile("^[1-9]\\d{5}\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}$");
    private static final int[] WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final char[] CHECK_CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    private boolean required;

    @Override
    public void initialize(IdCard annotation) {
        this.required = annotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return !required;
        }
        if (value.length() == 18) {
            return validate18(value);
        }
        if (value.length() == 15) {
            return ID_CARD_15.matcher(value).matches();
        }
        return false;
    }

    private boolean validate18(String idCard) {
        if (!ID_CARD_18.matcher(idCard).matches()) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (idCard.charAt(i) - '0') * WEIGHT[i];
        }
        char expectedCheckCode = CHECK_CODE[sum % 11];
        char actualCheckCode = Character.toUpperCase(idCard.charAt(17));
        return expectedCheckCode == actualCheckCode;
    }
}
