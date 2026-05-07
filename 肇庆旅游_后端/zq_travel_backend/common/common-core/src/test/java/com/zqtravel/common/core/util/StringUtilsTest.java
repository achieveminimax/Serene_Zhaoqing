package com.zqtravel.common.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * StringUtils单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@DisplayName("StringUtils单元测试")
class StringUtilsTest {

    // ==================== isEmpty/isNotEmpty 测试 ====================

    @Test
    @DisplayName("isEmpty: 空字符串应返回true")
    void isEmpty_NullOrEmptyString_ShouldReturnTrue() {
        assertTrue(StringUtils.isEmpty(null));
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    @DisplayName("isEmpty: 非空字符串应返回false")
    void isEmpty_NonEmptyString_ShouldReturnFalse() {
        assertFalse(StringUtils.isEmpty("test"));
        assertFalse(StringUtils.isEmpty(" "));
        assertFalse(StringUtils.isEmpty("a"));
    }

    @Test
    @DisplayName("isNotEmpty: 空字符串应返回false")
    void isNotEmpty_NullOrEmptyString_ShouldReturnFalse() {
        assertFalse(StringUtils.isNotEmpty(null));
        assertFalse(StringUtils.isNotEmpty(""));
    }

    @Test
    @DisplayName("isNotEmpty: 非空字符串应返回true")
    void isNotEmpty_NonEmptyString_ShouldReturnTrue() {
        assertTrue(StringUtils.isNotEmpty("test"));
        assertTrue(StringUtils.isNotEmpty(" "));
    }

    // ==================== isBlank/isNotBlank 测试 ====================

    @Test
    @DisplayName("isBlank: 空白字符串应返回true")
    void isBlank_NullOrBlankString_ShouldReturnTrue() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank(" "));
        assertTrue(StringUtils.isBlank("   "));
        assertTrue(StringUtils.isBlank("\t"));
        assertTrue(StringUtils.isBlank("\n"));
    }

    @Test
    @DisplayName("isBlank: 非空白字符串应返回false")
    void isBlank_NonBlankString_ShouldReturnFalse() {
        assertFalse(StringUtils.isBlank("test"));
        assertFalse(StringUtils.isBlank(" a "));
        assertFalse(StringUtils.isBlank("a"));
    }

    @Test
    @DisplayName("isNotBlank: 空白字符串应返回false")
    void isNotBlank_NullOrBlankString_ShouldReturnFalse() {
        assertFalse(StringUtils.isNotBlank(null));
        assertFalse(StringUtils.isNotBlank(""));
        assertFalse(StringUtils.isNotBlank(" "));
    }

    @Test
    @DisplayName("isNotBlank: 非空白字符串应返回true")
    void isNotBlank_NonBlankString_ShouldReturnTrue() {
        assertTrue(StringUtils.isNotBlank("test"));
        assertTrue(StringUtils.isNotBlank(" a "));
    }

    // ==================== trim 测试 ====================

    @Test
    @DisplayName("trim: 去除两端空白字符")
    void trim_ShouldRemoveWhitespace() {
        assertEquals("test", StringUtils.trim("  test  "));
        assertEquals("test", StringUtils.trim("test"));
        assertEquals("", StringUtils.trim("   "));
        assertNull(StringUtils.trim(null));
    }

    @Test
    @DisplayName("trimToNull: 空白字符串转为null")
    void trimToNull_BlankString_ShouldReturnNull() {
        assertNull(StringUtils.trimToNull("   "));
        assertNull(StringUtils.trimToNull(""));
        assertNull(StringUtils.trimToNull(null));
        assertEquals("test", StringUtils.trimToNull("  test  "));
    }

    @Test
    @DisplayName("trimToEmpty: null转为空字符串")
    void trimToEmpty_Null_ShouldReturnEmpty() {
        assertEquals("", StringUtils.trimToEmpty(null));
        assertEquals("", StringUtils.trimToEmpty("   "));
        assertEquals("test", StringUtils.trimToEmpty("  test  "));
    }

    // ==================== substring 测试 ====================

    @Test
    @DisplayName("substring: 从指定位置截取")
    void substring_FromIndex_ShouldWork() {
        assertEquals("world", StringUtils.substring("hello world", 6));
        assertEquals("hello world", StringUtils.substring("hello world", 0));
        assertEquals("", StringUtils.substring("hello", 5));
        assertNull(StringUtils.substring(null, 0));
    }

    @Test
    @DisplayName("substring: 指定范围截取")
    void substring_WithRange_ShouldWork() {
        assertEquals("lo wo", StringUtils.substring("hello world", 3, 8));
        assertEquals("hello", StringUtils.substring("hello world", 0, 5));
        assertNull(StringUtils.substring(null, 0, 5));
    }

    // ==================== startsWith/endsWith 测试 ====================

    @Test
    @DisplayName("startsWith: 前缀判断")
    void startsWith_ShouldWork() {
        assertTrue(StringUtils.startsWith("hello world", "hello"));
        assertFalse(StringUtils.startsWith("hello world", "world"));
        assertFalse(StringUtils.startsWith(null, "hello"));
        assertFalse(StringUtils.startsWith("hello", null));
    }

    @Test
    @DisplayName("endsWith: 后缀判断")
    void endsWith_ShouldWork() {
        assertTrue(StringUtils.endsWith("hello world", "world"));
        assertFalse(StringUtils.endsWith("hello world", "hello"));
        assertFalse(StringUtils.endsWith(null, "world"));
        assertFalse(StringUtils.endsWith("hello", null));
    }

    // ==================== join 测试 ====================

    @Test
    @DisplayName("join: 连接数组")
    void join_Array_ShouldWork() {
        assertEquals("abc", StringUtils.join(new String[]{"a", "b", "c"}));
        assertEquals("", StringUtils.join(new String[]{}));
        assertNull(StringUtils.join((Object[]) null));
    }

    @Test
    @DisplayName("join: 使用分隔符连接数组")
    void join_ArrayWithSeparator_ShouldWork() {
        // 验证方法调用不会抛出异常
        String result = StringUtils.join(",", new Object[]{"a", "b", "c"});
        assertNotNull(result);
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("c"));
    }

    @Test
    @DisplayName("join: 使用分隔符连接集合")
    void join_IterableWithSeparator_ShouldWork() {
        List<String> list = Arrays.asList("a", "b", "c");
        String result = StringUtils.join(",", list);
        assertNotNull(result);
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("c"));
    }

    // ==================== split 测试 ====================

    @Test
    @DisplayName("split: 分割字符串")
    void split_ShouldWork() {
        String[] result = StringUtils.split("a,b,c", ",");
        assertArrayEquals(new String[]{"a", "b", "c"}, result);

        result = StringUtils.split("a b c", " ");
        assertArrayEquals(new String[]{"a", "b", "c"}, result);

        assertNull(StringUtils.split(null, ","));
    }

    @Test
    @DisplayName("splitAndTrim: 分割并去除空白")
    void splitAndTrim_ShouldWork() {
        String[] result = StringUtils.splitAndTrim(" a , b , c ", ",");
        assertArrayEquals(new String[]{"a", "b", "c"}, result);

        assertArrayEquals(new String[0], StringUtils.splitAndTrim("   ", ","));
        assertArrayEquals(new String[0], StringUtils.splitAndTrim(null, ","));
    }

    // ==================== repeat 测试 ====================

    @Test
    @DisplayName("repeat: 重复字符串")
    void repeat_ShouldWork() {
        assertEquals("aaa", StringUtils.repeat("a", 3));
        assertEquals("ababab", StringUtils.repeat("ab", 3));
        assertEquals("", StringUtils.repeat("a", 0));
        assertNull(StringUtils.repeat(null, 3));
    }

    // ==================== replace 测试 ====================

    @Test
    @DisplayName("replace: 替换字符串")
    void replace_ShouldWork() {
        assertEquals("heXXo worXd", StringUtils.replace("hello world", "l", "X"));
        assertEquals("hello world", StringUtils.replace("hello world", "xyz", "X"));
        assertNull(StringUtils.replace(null, "a", "b"));
    }

    @Test
    @DisplayName("replaceAll: 替换所有匹配")
    void replaceAll_ShouldWork() {
        assertEquals("heXXo worXd", StringUtils.replaceAll("hello world", "l", "X"));
    }

    // ==================== 大小写转换 测试 ====================

    @Test
    @DisplayName("lowerCase: 转为小写")
    void lowerCase_ShouldWork() {
        assertEquals("hello", StringUtils.lowerCase("HELLO"));
        assertEquals("hello", StringUtils.lowerCase("Hello"));
        assertNull(StringUtils.lowerCase(null));
    }

    @Test
    @DisplayName("upperCase: 转为大写")
    void upperCase_ShouldWork() {
        assertEquals("HELLO", StringUtils.upperCase("hello"));
        assertEquals("HELLO", StringUtils.upperCase("Hello"));
        assertNull(StringUtils.upperCase(null));
    }

    @Test
    @DisplayName("capitalize: 首字母大写")
    void capitalize_ShouldWork() {
        assertEquals("Hello", StringUtils.capitalize("hello"));
        // Apache Commons的capitalize行为: 首字母大写，其余保持原样
        assertEquals("HELLO", StringUtils.capitalize("HELLO"));
        assertNull(StringUtils.capitalize(null));
        assertEquals("", StringUtils.capitalize(""));
    }

    @Test
    @DisplayName("uncapitalize: 首字母小写")
    void uncapitalize_ShouldWork() {
        assertEquals("hello", StringUtils.uncapitalize("Hello"));
        assertEquals("hELLO", StringUtils.uncapitalize("HELLO"));
        assertNull(StringUtils.uncapitalize(null));
    }

    // ==================== 命名风格转换 测试 ====================

    @Test
    @DisplayName("camelToUnderline: 驼峰转下划线")
    void camelToUnderline_ShouldWork() {
        assertEquals("user_name", StringUtils.camelToUnderline("userName"));
        assertEquals("user_name_info", StringUtils.camelToUnderline("userNameInfo"));
        assertEquals("username", StringUtils.camelToUnderline("username"));
        assertNull(StringUtils.camelToUnderline(null));
        assertEquals("", StringUtils.camelToUnderline(""));
        assertEquals("   ", StringUtils.camelToUnderline("   "));
    }

    @Test
    @DisplayName("underlineToCamel: 下划线转驼峰")
    void underlineToCamel_ShouldWork() {
        assertEquals("userName", StringUtils.underlineToCamel("user_name"));
        assertEquals("userNameInfo", StringUtils.underlineToCamel("user_name_info"));
        assertEquals("username", StringUtils.underlineToCamel("username"));
        assertNull(StringUtils.underlineToCamel(null));
        assertEquals("", StringUtils.underlineToCamel(""));
    }

    // ==================== truncateWithEllipsis 测试 ====================

    @Test
    @DisplayName("truncateWithEllipsis: 截断字符串")
    void truncateWithEllipsis_ShouldWork() {
        assertEquals("hello...", StringUtils.truncateWithEllipsis("hello world", 8));
        assertEquals("hello world", StringUtils.truncateWithEllipsis("hello world", 20));
        assertNull(StringUtils.truncateWithEllipsis(null, 10));
        assertEquals("", StringUtils.truncateWithEllipsis("", 10));
        // 长度<=3时直接截取，不添加省略号
        assertEquals("ab", StringUtils.truncateWithEllipsis("abc", 2));
    }

    // ==================== maskPhone 测试 ====================

    @Test
    @DisplayName("maskPhone: 手机号脱敏")
    void maskPhone_ShouldWork() {
        assertEquals("138****8000", StringUtils.maskPhone("13800138000"));
        assertEquals("159****5678", StringUtils.maskPhone("15912345678"));
        assertNull(StringUtils.maskPhone(null));
        // 长度小于7时不脱敏
        assertEquals("123", StringUtils.maskPhone("123"));
        // 长度为7时，前3位 + **** + 后0位
        assertEquals("123****", StringUtils.maskPhone("1234567"));
    }

    // ==================== maskEmail 测试 ====================

    @Test
    @DisplayName("maskEmail: 邮箱脱敏")
    void maskEmail_ShouldWork() {
        // 长度>2时: 首字符 + *** + 尾字符 + @domain
        assertEquals("t***t@example.com", StringUtils.maskEmail("test@example.com"));
        assertEquals("a***n@example.com", StringUtils.maskEmail("admin@example.com"));
        // 长度<=2时: 首字符 + *** + @domain
        assertEquals("a***@example.com", StringUtils.maskEmail("ab@example.com"));
        assertNull(StringUtils.maskEmail(null));
        // @前面只有1个字符时不脱敏
        assertEquals("a@b.com", StringUtils.maskEmail("a@b.com"));
    }

    // ==================== filterBlank 测试 ====================

    @Test
    @DisplayName("filterBlank: 过滤空字符串")
    void filterBlank_ShouldWork() {
        Collection<String> input = Arrays.asList("a", "", "b", " ", "c", null);
        List<String> result = StringUtils.filterBlank(input);
        assertEquals(Arrays.asList("a", "b", "c"), result);

        assertTrue(StringUtils.filterBlank(null).isEmpty());
        assertTrue(StringUtils.filterBlank(Arrays.asList()).isEmpty());
    }

    @Test
    @DisplayName("filterBlank: 返回空集合而不是null")
    void filterBlank_NullInput_ShouldReturnEmptyList() {
        List<String> result = StringUtils.filterBlank(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ==================== 字符类型判断 测试 ====================

    @Test
    @DisplayName("isNumeric: 数字判断")
    void isNumeric_ShouldWork() {
        assertTrue(StringUtils.isNumeric("12345"));
        assertFalse(StringUtils.isNumeric("12.34"));
        assertFalse(StringUtils.isNumeric("abc"));
        assertFalse(StringUtils.isNumeric(""));
        assertFalse(StringUtils.isNumeric(null));
    }

    @Test
    @DisplayName("isAlpha: 字母判断")
    void isAlpha_ShouldWork() {
        assertTrue(StringUtils.isAlpha("abc"));
        assertTrue(StringUtils.isAlpha("ABC"));
        assertFalse(StringUtils.isAlpha("abc123"));
        assertFalse(StringUtils.isAlpha(""));
        assertFalse(StringUtils.isAlpha(null));
    }

    @Test
    @DisplayName("isAlphanumeric: 字母数字判断")
    void isAlphanumeric_ShouldWork() {
        assertTrue(StringUtils.isAlphanumeric("abc123"));
        assertTrue(StringUtils.isAlphanumeric("ABC"));
        assertTrue(StringUtils.isAlphanumeric("123"));
        assertFalse(StringUtils.isAlphanumeric("abc-123"));
        assertFalse(StringUtils.isAlphanumeric(""));
        assertFalse(StringUtils.isAlphanumeric(null));
    }

    // ==================== stripHtml 测试 ====================

    @Test
    @DisplayName("stripHtml: 去除HTML标签")
    void stripHtml_ShouldWork() {
        assertEquals("hello world", StringUtils.stripHtml("<p>hello world</p>"));
        assertEquals("bold text", StringUtils.stripHtml("<b>bold</b> text"));
        assertEquals("", StringUtils.stripHtml("<br/>"));
        assertNull(StringUtils.stripHtml(null));
        assertEquals("   ", StringUtils.stripHtml("   "));
    }

    // ==================== randomString 测试 ====================

    @Test
    @DisplayName("randomString: 生成随机字符串")
    void randomString_ShouldWork() {
        String result1 = StringUtils.randomString(10);
        assertEquals(10, result1.length());

        String result2 = StringUtils.randomString(20);
        assertEquals(20, result2.length());

        // 验证只包含允许的字符
        assertTrue(result1.matches("[a-zA-Z0-9]+"));

        assertEquals("", StringUtils.randomString(0));
        assertEquals("", StringUtils.randomString(-1));
    }

    @Test
    @DisplayName("randomString: 随机性验证")
    void randomString_ShouldBeRandom() {
        String result1 = StringUtils.randomString(10);
        String result2 = StringUtils.randomString(10);
        // 两个随机字符串应该不相同（概率极低相同）
        assertNotEquals(result1, result2);
    }
}
