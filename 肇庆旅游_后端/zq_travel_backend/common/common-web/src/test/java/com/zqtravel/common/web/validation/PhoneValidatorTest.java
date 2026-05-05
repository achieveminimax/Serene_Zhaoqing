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
class PhoneValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    private PhoneValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PhoneValidator();
        Phone phone = createPhoneAnnotation(true);
        validator.initialize(phone);
    }

    @Test
    @DisplayName("有效的手机号应该通过验证")
    void validPhone_ShouldPass() {
        assertTrue(validator.isValid("13800138000", context));
        assertTrue(validator.isValid("15912345678", context));
        assertTrue(validator.isValid("18612345678", context));
    }

    @Test
    @DisplayName("无效的手机号应该不通过验证")
    void invalidPhone_ShouldFail() {
        assertFalse(validator.isValid("12345678901", context));
        assertFalse(validator.isValid("1380013800", context));
        assertFalse(validator.isValid("138001380001", context));
        assertFalse(validator.isValid("abcdefghijk", context));
    }

    @Test
    @DisplayName("空值且必填时应该不通过验证")
    void nullValue_WhenRequired_ShouldFail() {
        assertFalse(validator.isValid(null, context));
        assertFalse(validator.isValid("", context));
    }

    @Test
    @DisplayName("空值且非必填时应该通过验证")
    void nullValue_WhenNotRequired_ShouldPass() {
        PhoneValidator optionalValidator = new PhoneValidator();
        Phone phone = createPhoneAnnotation(false);
        optionalValidator.initialize(phone);

        assertTrue(optionalValidator.isValid(null, context));
        assertTrue(optionalValidator.isValid("", context));
    }

    private Phone createPhoneAnnotation(boolean required) {
        return new Phone() {
            @Override
            public String message() {
                return "手机号格式不正确";
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
            public boolean required() {
                return required;
            }
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return Phone.class;
            }
        };
    }
}
