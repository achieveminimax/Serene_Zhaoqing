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
class IdCardValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    private IdCardValidator validator;

    @BeforeEach
    void setUp() {
        validator = new IdCardValidator();
        IdCard idCard = createIdCardAnnotation(true);
        validator.initialize(idCard);
    }

    @Test
    @DisplayName("有效的18位身份证号应该通过验证")
    void valid18DigitIdCard_ShouldPass() {
        assertTrue(validator.isValid("110101199003076034", context));
    }

    @Test
    @DisplayName("无效的18位身份证号应该不通过验证")
    void invalid18DigitIdCard_ShouldFail() {
        assertFalse(validator.isValid("110101199003076035", context));
        assertFalse(validator.isValid("123456789012345678", context));
    }

    @Test
    @DisplayName("有效的15位身份证号应该通过验证")
    void valid15DigitIdCard_ShouldPass() {
        assertTrue(validator.isValid("110101900307773", context));
    }

    @Test
    @DisplayName("长度不正确的身份证号应该不通过验证")
    void wrongLength_ShouldFail() {
        assertFalse(validator.isValid("1101011990030777", context));
        assertFalse(validator.isValid("1101011990030777341", context));
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
        IdCardValidator optionalValidator = new IdCardValidator();
        IdCard idCard = createIdCardAnnotation(false);
        optionalValidator.initialize(idCard);

        assertTrue(optionalValidator.isValid(null, context));
        assertTrue(optionalValidator.isValid("", context));
    }

    private IdCard createIdCardAnnotation(boolean required) {
        return new IdCard() {
            @Override
            public String message() {
                return "身份证号格式不正确";
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
                return IdCard.class;
            }
        };
    }
}
