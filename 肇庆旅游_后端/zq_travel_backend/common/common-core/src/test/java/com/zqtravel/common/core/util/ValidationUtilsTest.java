package com.zqtravel.common.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.zqtravel.common.core.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ValidationUtils单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@DisplayName("ValidationUtils单元测试")
class ValidationUtilsTest {

    // ==================== notNull/isNull 测试 ====================

    @Test
    @DisplayName("notNull: 对象不为null时不抛出异常")
    void notNull_NonNullObject_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.notNull("test", "对象不能为空"));
        assertDoesNotThrow(() -> ValidationUtils.notNull(123, "对象不能为空"));
        assertDoesNotThrow(() -> ValidationUtils.notNull(new Object(), "对象不能为空"));
    }

    @Test
    @DisplayName("notNull: 对象为null时抛出BusinessException")
    void notNull_NullObject_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notNull(null, "对象不能为空"));
        assertEquals("40001", exception.getCode());
        assertEquals("对象不能为空", exception.getMessage());
    }

    @Test
    @DisplayName("isNull: 对象为null时不抛出异常")
    void isNull_NullObject_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.isNull(null, "对象必须为空"));
    }

    @Test
    @DisplayName("isNull: 对象不为null时抛出BusinessException")
    void isNull_NonNullObject_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.isNull("test", "对象必须为空"));
        assertEquals("40001", exception.getCode());
    }

    // ==================== notBlank/isBlank 测试 ====================

    @Test
    @DisplayName("notBlank: 字符串不为空白时不抛出异常")
    void notBlank_NonBlankString_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.notBlank("test", "字符串不能为空"));
        assertDoesNotThrow(() -> ValidationUtils.notBlank(" a ", "字符串不能为空"));
    }

    @Test
    @DisplayName("notBlank: 字符串为null时抛出BusinessException")
    void notBlank_NullString_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notBlank(null, "字符串不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("notBlank: 字符串为空时抛出BusinessException")
    void notBlank_EmptyString_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notBlank("", "字符串不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("notBlank: 字符串为空白时抛出BusinessException")
    void notBlank_BlankString_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notBlank("   ", "字符串不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("isBlank: 字符串为空白时不抛出异常")
    void isBlank_BlankString_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.isBlank(null, "字符串必须为空"));
        assertDoesNotThrow(() -> ValidationUtils.isBlank("", "字符串必须为空"));
        assertDoesNotThrow(() -> ValidationUtils.isBlank("   ", "字符串必须为空"));
    }

    @Test
    @DisplayName("isBlank: 字符串不为空白时抛出BusinessException")
    void isBlank_NonBlankString_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.isBlank("test", "字符串必须为空"));
        assertEquals("40001", exception.getCode());
    }

    // ==================== notEmpty/isEmpty (Collection) 测试 ====================

    @Test
    @DisplayName("notEmpty: 集合不为空时不抛出异常")
    void notEmpty_NonEmptyCollection_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.notEmpty(Arrays.asList("a"), "集合不能为空"));
        assertDoesNotThrow(() -> ValidationUtils.notEmpty(Collections.singletonList(1), "集合不能为空"));
    }

    @Test
    @DisplayName("notEmpty: 集合为null时抛出BusinessException")
    void notEmpty_NullCollection_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notEmpty((Collection<?>) null, "集合不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("notEmpty: 集合为空时抛出BusinessException")
    void notEmpty_EmptyCollection_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notEmpty(Collections.emptyList(), "集合不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("isEmpty: 集合为空时不抛出异常")
    void isEmpty_EmptyCollection_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.isEmpty((Collection<?>) null, "集合必须为空"));
        assertDoesNotThrow(() -> ValidationUtils.isEmpty(Collections.emptyList(), "集合必须为空"));
    }

    @Test
    @DisplayName("isEmpty: 集合不为空时抛出BusinessException")
    void isEmpty_NonEmptyCollection_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.isEmpty(Arrays.asList("a"), "集合必须为空"));
        assertEquals("40001", exception.getCode());
    }

    // ==================== notEmpty/isEmpty (Map) 测试 ====================

    @Test
    @DisplayName("notEmpty: Map不为空时不抛出异常")
    void notEmpty_NonEmptyMap_ShouldNotThrow() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertDoesNotThrow(() -> ValidationUtils.notEmpty(map, "Map不能为空"));
    }

    @Test
    @DisplayName("notEmpty: Map为null时抛出BusinessException")
    void notEmpty_NullMap_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notEmpty((Map<?, ?>) null, "Map不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("notEmpty: Map为空时抛出BusinessException")
    void notEmpty_EmptyMap_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notEmpty(Collections.emptyMap(), "Map不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("isEmpty: Map为空时不抛出异常")
    void isEmpty_EmptyMap_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.isEmpty((Map<?, ?>) null, "Map必须为空"));
        assertDoesNotThrow(() -> ValidationUtils.isEmpty(Collections.emptyMap(), "Map必须为空"));
    }

    // ==================== notEmpty/isEmpty (Array) 测试 ====================

    @Test
    @DisplayName("notEmpty: 数组不为空时不抛出异常")
    void notEmpty_NonEmptyArray_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.notEmpty(new String[]{"a"}, "数组不能为空"));
        assertDoesNotThrow(() -> ValidationUtils.notEmpty(new Integer[]{1, 2}, "数组不能为空"));
    }

    @Test
    @DisplayName("notEmpty: 数组为null时抛出BusinessException")
    void notEmpty_NullArray_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notEmpty((Object[]) null, "数组不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("notEmpty: 数组为空时抛出BusinessException")
    void notEmpty_EmptyArray_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notEmpty(new String[]{}, "数组不能为空"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("isEmpty: 数组为空时不抛出异常")
    void isEmpty_EmptyArray_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.isEmpty((Object[]) null, "数组必须为空"));
        assertDoesNotThrow(() -> ValidationUtils.isEmpty(new String[]{}, "数组必须为空"));
    }

    // ==================== isTrue/isFalse 测试 ====================

    @Test
    @DisplayName("isTrue: 条件为true时不抛出异常")
    void isTrue_TrueCondition_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.isTrue(true, "条件必须为true"));
        assertDoesNotThrow(() -> ValidationUtils.isTrue(1 == 1, "条件必须为true"));
    }

    @Test
    @DisplayName("isTrue: 条件为false时抛出BusinessException")
    void isTrue_FalseCondition_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.isTrue(false, "条件必须为true"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("isFalse: 条件为false时不抛出异常")
    void isFalse_FalseCondition_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.isFalse(false, "条件必须为false"));
        assertDoesNotThrow(() -> ValidationUtils.isFalse(1 == 2, "条件必须为false"));
    }

    @Test
    @DisplayName("isFalse: 条件为true时抛出BusinessException")
    void isFalse_TrueCondition_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.isFalse(true, "条件必须为false"));
        assertEquals("40001", exception.getCode());
    }

    // ==================== equals/notEquals 测试 ====================

    @Test
    @DisplayName("equals: 对象相等时不抛出异常")
    void equals_EqualObjects_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.equals("test", "test", "对象必须相等"));
        assertDoesNotThrow(() -> ValidationUtils.equals(null, null, "对象必须相等"));
        assertDoesNotThrow(() -> ValidationUtils.equals(123, 123, "对象必须相等"));
    }

    @Test
    @DisplayName("equals: 对象不相等时抛出BusinessException")
    void equals_NotEqualObjects_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.equals("test", "other", "对象必须相等"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("equals: 一个为null一个不为null时抛出BusinessException")
    void equals_OneNull_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.equals(null, "test", "对象必须相等"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("notEquals: 对象不相等时不抛出异常")
    void notEquals_NotEqualObjects_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.notEquals("test", "other", "对象必须不相等"));
        assertDoesNotThrow(() -> ValidationUtils.notEquals(null, "test", "对象必须不相等"));
        assertDoesNotThrow(() -> ValidationUtils.notEquals("test", null, "对象必须不相等"));
    }

    @Test
    @DisplayName("notEquals: 对象相等时抛出BusinessException")
    void notEquals_EqualObjects_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notEquals("test", "test", "对象必须不相等"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("notEquals: 两个都为null时抛出BusinessException")
    void notEquals_BothNull_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.notEquals(null, null, "对象必须不相等"));
        assertEquals("40001", exception.getCode());
    }

    // ==================== matches 测试 ====================

    @Test
    @DisplayName("matches: 字符串匹配正则时不抛出异常")
    void matches_MatchingPattern_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.matches("abc123", "^[a-z0-9]+$", "格式不正确"));
    }

    @Test
    @DisplayName("matches: 字符串不匹配正则时抛出BusinessException")
    void matches_NotMatchingPattern_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.matches("ABC", "^[a-z]+$", "格式不正确"));
        assertEquals("40001", exception.getCode());
    }

    @Test
    @DisplayName("matches: 字符串为null时抛出BusinessException")
    void matches_NullString_ShouldThrowException() {
        BusinessException exception = assertThrows(BusinessException.class,
                () -> ValidationUtils.matches(null, "^[a-z]+$", "格式不正确"));
        assertEquals("40001", exception.getCode());
    }

    // ==================== validatePhone 测试 ====================

    @Test
    @DisplayName("validatePhone: 有效手机号不抛出异常")
    void validatePhone_ValidPhone_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validatePhone("13800138000", "手机号格式不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validatePhone("15912345678", "手机号格式不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validatePhone("18612345678", "手机号格式不正确"));
    }

    @Test
    @DisplayName("validatePhone: 无效手机号抛出BusinessException")
    void validatePhone_InvalidPhone_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validatePhone("12345678901", "手机号格式不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validatePhone("1380013800", "手机号格式不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validatePhone("abcdefghijk", "手机号格式不正确"));
    }

    // ==================== validateEmail 测试 ====================

    @Test
    @DisplayName("validateEmail: 有效邮箱不抛出异常")
    void validateEmail_ValidEmail_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateEmail("test@example.com", "邮箱格式不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateEmail("user.name@domain.co.uk", "邮箱格式不正确"));
    }

    @Test
    @DisplayName("validateEmail: 无效邮箱抛出BusinessException")
    void validateEmail_InvalidEmail_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateEmail("invalid-email", "邮箱格式不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateEmail("@example.com", "邮箱格式不正确"));
    }

    // ==================== validateIdCard 测试 ====================

    @Test
    @DisplayName("validateIdCard: 有效18位身份证号不抛出异常")
    void validateIdCard_Valid18Digit_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateIdCard("110101199003076034", "身份证号格式不正确"));
    }

    @Test
    @DisplayName("validateIdCard: 无效身份证号抛出BusinessException")
    void validateIdCard_InvalidIdCard_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateIdCard("123456789012345678", "身份证号格式不正确"));
    }

    // ==================== validateUrl 测试 ====================

    @Test
    @DisplayName("validateUrl: 有效URL不抛出异常")
    void validateUrl_ValidUrl_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateUrl("https://www.example.com", "URL格式不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateUrl("http://localhost:8080/api", "URL格式不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateUrl("ftp://files.example.com", "URL格式不正确"));
    }

    @Test
    @DisplayName("validateUrl: 无效URL抛出BusinessException")
    void validateUrl_InvalidUrl_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateUrl("not-a-url", "URL格式不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateUrl("www.example.com", "URL格式不正确"));
    }

    // ==================== validateIp 测试 ====================

    @Test
    @DisplayName("validateIp: 有效IP地址不抛出异常")
    void validateIp_ValidIp_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateIp("192.168.1.1", "IP格式不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateIp("10.0.0.1", "IP格式不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateIp("255.255.255.255", "IP格式不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateIp("0.0.0.0", "IP格式不正确"));
    }

    @Test
    @DisplayName("validateIp: 无效IP地址抛出BusinessException")
    void validateIp_InvalidIp_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateIp("256.1.1.1", "IP格式不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateIp("192.168.1", "IP格式不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateIp("not-an-ip", "IP格式不正确"));
    }

    // ==================== validateLength 测试 ====================

    @Test
    @DisplayName("validateLength: 长度在范围内不抛出异常")
    void validateLength_ValidLength_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateLength("abc", 2, 5, "长度不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateLength("abcd", 2, 5, "长度不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateLength("abcde", 2, 5, "长度不正确"));
    }

    @Test
    @DisplayName("validateLength: 长度不在范围内抛出BusinessException")
    void validateLength_InvalidLength_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateLength("a", 2, 5, "长度不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateLength("abcdef", 2, 5, "长度不正确"));
    }

    @Test
    @DisplayName("validateLength: null字符串抛出BusinessException")
    void validateLength_NullString_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateLength(null, 2, 5, "长度不正确"));
    }

    // ==================== validateMinLength 测试 ====================

    @Test
    @DisplayName("validateMinLength: 长度不小于最小值不抛出异常")
    void validateMinLength_ValidLength_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateMinLength("abc", 3, "长度不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateMinLength("abcd", 3, "长度不正确"));
    }

    @Test
    @DisplayName("validateMinLength: 长度小于最小值抛出BusinessException")
    void validateMinLength_InvalidLength_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateMinLength("ab", 3, "长度不正确"));
    }

    // ==================== validateMaxLength 测试 ====================

    @Test
    @DisplayName("validateMaxLength: 长度不大于最大值不抛出异常")
    void validateMaxLength_ValidLength_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateMaxLength("abc", 3, "长度不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateMaxLength("ab", 3, "长度不正确"));
    }

    @Test
    @DisplayName("validateMaxLength: 长度大于最大值抛出BusinessException")
    void validateMaxLength_InvalidLength_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateMaxLength("abcd", 3, "长度不正确"));
    }

    // ==================== validateRange 测试 ====================

    @Test
    @DisplayName("validateRange: 数值在范围内不抛出异常")
    void validateRange_ValidRange_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateRange(5, 1, 10, "数值范围不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateRange(1, 1, 10, "数值范围不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateRange(10, 1, 10, "数值范围不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateRange(5.5, 1.0, 10.0, "数值范围不正确"));
    }

    @Test
    @DisplayName("validateRange: 数值不在范围内抛出BusinessException")
    void validateRange_InvalidRange_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateRange(0, 1, 10, "数值范围不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateRange(11, 1, 10, "数值范围不正确"));
    }

    @Test
    @DisplayName("validateRange: null数值抛出BusinessException")
    void validateRange_NullNumber_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateRange(null, 1, 10, "数值范围不正确"));
    }

    // ==================== validateMin 测试 ====================

    @Test
    @DisplayName("validateMin: 数值不小于最小值不抛出异常")
    void validateMin_ValidValue_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateMin(5, 5, "数值不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateMin(10, 5, "数值不正确"));
    }

    @Test
    @DisplayName("validateMin: 数值小于最小值抛出BusinessException")
    void validateMin_InvalidValue_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateMin(4, 5, "数值不正确"));
    }

    // ==================== validateMax 测试 ====================

    @Test
    @DisplayName("validateMax: 数值不大于最大值不抛出异常")
    void validateMax_ValidValue_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateMax(5, 5, "数值不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateMax(4, 5, "数值不正确"));
    }

    @Test
    @DisplayName("validateMax: 数值大于最大值抛出BusinessException")
    void validateMax_InvalidValue_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateMax(6, 5, "数值不正确"));
    }

    // ==================== validateSize 测试 ====================

    @Test
    @DisplayName("validateSize: 集合大小在范围内不抛出异常")
    void validateSize_ValidSize_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateSize(Arrays.asList(1, 2, 3), 2, 5, "大小不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateSize(Arrays.asList(1, 2), 2, 5, "大小不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateSize(Arrays.asList(1, 2, 3, 4, 5), 2, 5, "大小不正确"));
    }

    @Test
    @DisplayName("validateSize: 集合大小不在范围内抛出BusinessException")
    void validateSize_InvalidSize_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateSize(Arrays.asList(1), 2, 5, "大小不正确"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateSize(Arrays.asList(1, 2, 3, 4, 5, 6), 2, 5, "大小不正确"));
    }

    // ==================== validateMinSize 测试 ====================

    @Test
    @DisplayName("validateMinSize: 集合大小不小于最小值不抛出异常")
    void validateMinSize_ValidSize_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateMinSize(Arrays.asList(1, 2, 3), 3, "大小不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateMinSize(Arrays.asList(1, 2, 3, 4), 3, "大小不正确"));
    }

    @Test
    @DisplayName("validateMinSize: 集合大小小于最小值抛出BusinessException")
    void validateMinSize_InvalidSize_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateMinSize(Arrays.asList(1, 2), 3, "大小不正确"));
    }

    // ==================== validateMaxSize 测试 ====================

    @Test
    @DisplayName("validateMaxSize: 集合大小不大于最大值不抛出异常")
    void validateMaxSize_ValidSize_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateMaxSize(Arrays.asList(1, 2, 3), 3, "大小不正确"));
        assertDoesNotThrow(() -> ValidationUtils.validateMaxSize(Arrays.asList(1, 2), 3, "大小不正确"));
    }

    @Test
    @DisplayName("validateMaxSize: 集合大小大于最大值抛出BusinessException")
    void validateMaxSize_InvalidSize_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateMaxSize(Arrays.asList(1, 2, 3, 4), 3, "大小不正确"));
    }

    // ==================== validateDateRange 测试 ====================

    @Test
    @DisplayName("validateDateRange: 日期在范围内不抛出异常")
    void validateDateRange_ValidRange_ShouldNotThrow() {
        LocalDate date = LocalDate.of(2026, 5, 5);
        LocalDate start = LocalDate.of(2026, 5, 1);
        LocalDate end = LocalDate.of(2026, 5, 10);
        assertDoesNotThrow(() -> ValidationUtils.validateDateRange(date, start, end, "日期范围不正确"));
    }

    @Test
    @DisplayName("validateDateRange: 日期不在范围内抛出BusinessException")
    void validateDateRange_InvalidRange_ShouldThrowException() {
        LocalDate date = LocalDate.of(2026, 5, 15);
        LocalDate start = LocalDate.of(2026, 5, 1);
        LocalDate end = LocalDate.of(2026, 5, 10);
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateDateRange(date, start, end, "日期范围不正确"));
    }

    @Test
    @DisplayName("validateDateRange: null日期抛出BusinessException")
    void validateDateRange_NullDate_ShouldThrowException() {
        LocalDate start = LocalDate.of(2026, 5, 1);
        LocalDate end = LocalDate.of(2026, 5, 10);
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateDateRange(null, start, end, "日期范围不正确"));
    }

    // ==================== validateDateTimeRange 测试 ====================

    @Test
    @DisplayName("validateDateTimeRange: 日期时间在范围内不抛出异常")
    void validateDateTimeRange_ValidRange_ShouldNotThrow() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 5, 5, 12, 0);
        LocalDateTime start = LocalDateTime.of(2026, 5, 5, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 5, 5, 14, 0);
        assertDoesNotThrow(() -> ValidationUtils.validateDateTimeRange(dateTime, start, end, "日期时间范围不正确"));
    }

    @Test
    @DisplayName("validateDateTimeRange: 日期时间不在范围内抛出BusinessException")
    void validateDateTimeRange_InvalidRange_ShouldThrowException() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 5, 5, 15, 0);
        LocalDateTime start = LocalDateTime.of(2026, 5, 5, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 5, 5, 14, 0);
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateDateTimeRange(dateTime, start, end, "日期时间范围不正确"));
    }

    // ==================== validateNumeric 测试 ====================

    @Test
    @DisplayName("validateNumeric: 纯数字字符串不抛出异常")
    void validateNumeric_NumericString_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateNumeric("12345", "必须为数字"));
        assertDoesNotThrow(() -> ValidationUtils.validateNumeric("0", "必须为数字"));
    }

    @Test
    @DisplayName("validateNumeric: 非纯数字字符串抛出BusinessException")
    void validateNumeric_NonNumericString_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateNumeric("123.45", "必须为数字"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateNumeric("abc", "必须为数字"));
    }

    // ==================== validateLetter 测试 ====================

    @Test
    @DisplayName("validateLetter: 纯字母字符串不抛出异常")
    void validateLetter_LetterString_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateLetter("abc", "必须为字母"));
        assertDoesNotThrow(() -> ValidationUtils.validateLetter("ABC", "必须为字母"));
    }

    @Test
    @DisplayName("validateLetter: 非纯字母字符串抛出BusinessException")
    void validateLetter_NonLetterString_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateLetter("abc123", "必须为字母"));
    }

    // ==================== validateAlphanumeric 测试 ====================

    @Test
    @DisplayName("validateAlphanumeric: 字母数字字符串不抛出异常")
    void validateAlphanumeric_AlphanumericString_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateAlphanumeric("abc123", "必须为字母数字"));
        assertDoesNotThrow(() -> ValidationUtils.validateAlphanumeric("ABC", "必须为字母数字"));
        assertDoesNotThrow(() -> ValidationUtils.validateAlphanumeric("123", "必须为字母数字"));
    }

    @Test
    @DisplayName("validateAlphanumeric: 非字母数字字符串抛出BusinessException")
    void validateAlphanumeric_NonAlphanumericString_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateAlphanumeric("abc-123", "必须为字母数字"));
    }

    // ==================== validateChinese 测试 ====================

    @Test
    @DisplayName("validateChinese: 纯中文字符串不抛出异常")
    void validateChinese_ChineseString_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateChinese("中文", "必须为中文"));
        assertDoesNotThrow(() -> ValidationUtils.validateChinese("肇庆旅游", "必须为中文"));
    }

    @Test
    @DisplayName("validateChinese: 非纯中文字符串抛出BusinessException")
    void validateChinese_NonChineseString_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateChinese("中文123", "必须为中文"));
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateChinese("abc", "必须为中文"));
    }

    // ==================== validateFileSize 测试 ====================

    @Test
    @DisplayName("validateFileSize: 文件大小不超过限制不抛出异常")
    void validateFileSize_ValidSize_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateFileSize(1024, 1024 * 1024, "文件过大"));
        assertDoesNotThrow(() -> ValidationUtils.validateFileSize(0, 1024, "文件过大"));
    }

    @Test
    @DisplayName("validateFileSize: 文件大小超过限制抛出BusinessException")
    void validateFileSize_InvalidSize_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateFileSize(1025, 1024, "文件过大"));
    }

    // ==================== validateFileExtension 测试 ====================

    @Test
    @DisplayName("validateFileExtension: 允许的文件扩展名不抛出异常")
    void validateFileExtension_ValidExtension_ShouldNotThrow() {
        assertDoesNotThrow(() -> ValidationUtils.validateFileExtension("test.jpg",
                new String[]{"jpg", "png", "gif"}, "文件类型不允许"));
        assertDoesNotThrow(() -> ValidationUtils.validateFileExtension("test.PNG",
                new String[]{"jpg", "png", "gif"}, "文件类型不允许"));
    }

    @Test
    @DisplayName("validateFileExtension: 不允许的文件扩展名抛出BusinessException")
    void validateFileExtension_InvalidExtension_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateFileExtension("test.exe",
                        new String[]{"jpg", "png", "gif"}, "文件类型不允许"));
    }

    @Test
    @DisplayName("validateFileExtension: 无扩展名文件抛出BusinessException")
    void validateFileExtension_NoExtension_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateFileExtension("test",
                        new String[]{"jpg", "png"}, "文件类型不允许"));
    }

    @Test
    @DisplayName("validateFileExtension: null文件名抛出BusinessException")
    void validateFileExtension_NullFilename_ShouldThrowException() {
        assertThrows(BusinessException.class,
                () -> ValidationUtils.validateFileExtension(null,
                        new String[]{"jpg", "png"}, "文件类型不允许"));
    }
}
