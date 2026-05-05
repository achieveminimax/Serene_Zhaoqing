package com.zqtravel.common.web.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
        Password password = createPasswordAnnotation(6, 20, true, true);
        validator.initialize(password);
    }

    @Test
    @DisplayName("有效的密码应该通过验证")
    void validPassword_ShouldPass() {
        assertTrue(validator.isValid("abc123", context));
        assertTrue(validator.isValid("Test1234", context));
        assertTrue(validator.isValid("MyPassword1", context));
    }

    @Test
    @DisplayName("纯字母密码应该不通过验证")
    void onlyLetters_ShouldFail() {
        assertFalse(validator.isValid("abcdef", context));
    }

    @Test
    @DisplayName("纯数字密码应该不通过验证")
    void onlyDigits_ShouldFail() {
        assertFalse(validator.isValid("123456", context));
    }

    @Test
    @DisplayName("太短的密码应该不通过验证")
    void tooShort_ShouldFail() {
        assertFalse(validator.isValid("ab1", context));
    }

    @Test
    @DisplayName("太长的密码应该不通过验证")
    void tooLong_ShouldFail() {
        assertFalse(validator.isValid("a1".repeat(11), context));
    }

    @Test
    @DisplayName("空密码应该不通过验证")
    void nullPassword_ShouldFail() {
        assertFalse(validator.isValid(null, context));
        assertFalse(validator.isValid("", context));
    }

    private Password createPasswordAnnotation(int minLength, int maxLength, boolean requireLetter, boolean requireDigit) {
        return new Password() {
            @Override
            public String message() {
                return "密码格式不正确";
            }
            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }
            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return new Class[0];
            }
            @Override
            public int minLength() {
                return minLength;
            }
            @Override
            public int maxLength() {
                return maxLength;
            }
            @Override
            public boolean requireLetter() {
                return requireLetter;
            }
            @Override
            public boolean requireDigit() {
                return requireDigit;
            }
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return Password.class;
            }
        };
    }
}
